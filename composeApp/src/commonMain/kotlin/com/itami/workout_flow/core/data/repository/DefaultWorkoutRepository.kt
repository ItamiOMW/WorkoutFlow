package com.itami.workout_flow.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.room.RoomRawQuery
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.map
import com.itami.workout_flow.core.data.local.database.WorkoutFlowDatabase
import com.itami.workout_flow.core.data.mapper.toWorkout
import com.itami.workout_flow.core.data.mapper.toWorkoutPreview
import com.itami.workout_flow.core.data.remote.workouts.WorkoutsApiService
import com.itami.workout_flow.core.data.remote.workouts.WorkoutsRemoteMediator
import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.core.domain.model.result.EmptyResult
import com.itami.workout_flow.core.domain.model.workout.ScheduledWorkout
import com.itami.workout_flow.core.domain.model.workout.Workout
import com.itami.workout_flow.core.domain.model.workout.WorkoutPreview
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutTimeFilter
import com.itami.workout_flow.model.WorkoutType
import com.itami.workout_flow.model.WorkoutsFilter
import com.itami.workout_flow.model.WorkoutsSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class DefaultWorkoutRepository(
    private val database: WorkoutFlowDatabase,
    private val workoutsApiService: WorkoutsApiService,
) : WorkoutRepository {

    private val workoutDao = database.workoutDao

    override fun observeScheduledWorkouts(): Flow<List<ScheduledWorkout>> {
        return workoutDao.observeScheduledWorkoutsPreviews().map { scheduledWorkoutsWithDetails ->
            scheduledWorkoutsWithDetails.map { scheduledWorkoutWithDetails ->
                ScheduledWorkout(
                    id = scheduledWorkoutWithDetails.scheduledWorkout.uuid,
                    workoutPreview = scheduledWorkoutWithDetails.workoutPreviewDbView.toWorkoutPreview(),
                    dayOfWeek = scheduledWorkoutWithDetails.scheduledWorkout.dayOfWeek
                )
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun observePagingWorkoutPreviews(
        query: String,
        workoutsSort: WorkoutsSort,
        workoutsFilter: WorkoutsFilter,
    ): Flow<PagingData<WorkoutPreview>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5,
                prefetchDistance = 2,
            ),
            initialKey = 1,
            remoteMediator = WorkoutsRemoteMediator(
                database = database,
                workoutsApiService = workoutsApiService,
                pageSize = 10,
                query = query,
                workoutsFilter = workoutsFilter,
                workoutsSort = workoutsSort,
            ),
            pagingSourceFactory = {
                workoutDao.getWorkoutsPreviewsPagingSource(
                    sqlQuery = getWorkoutsRawQuery(
                        query = query,
                        workoutTypes = workoutsFilter.selectedWorkoutTypes,
                        equipments = workoutsFilter.selectedEquipments,
                        muscles = workoutsFilter.selectedMuscles,
                        timeFilters = workoutsFilter.selectedTimeFilters,
                        sort = workoutsSort.name,
                        isPublic = true,
                        isCustom = null,
                        isFavorite = null,
                        limit = null
                    )
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toWorkoutPreview() }
        }
    }

    override fun observeCustomWorkoutPreviews(
        query: String,
        workoutsSort: WorkoutsSort,
        workoutsFilter: WorkoutsFilter,
    ): Flow<List<WorkoutPreview>> {
        return workoutDao.observeWorkoutsPreviews(
            sqlQuery = getWorkoutsRawQuery(
                query = query,
                workoutTypes = workoutsFilter.selectedWorkoutTypes,
                equipments = workoutsFilter.selectedEquipments,
                muscles = workoutsFilter.selectedMuscles,
                timeFilters = workoutsFilter.selectedTimeFilters,
                sort = workoutsSort.name,
                isPublic = null,
                isCustom = true,
                isFavorite = null,
                limit = null
            )
        ).map { workoutPreviewDbViews ->
            workoutPreviewDbViews.map { it.toWorkoutPreview() }
        }
    }

    override fun observeFavoriteWorkoutPreviews(
        query: String,
        workoutsSort: WorkoutsSort,
        workoutsFilter: WorkoutsFilter,
    ): Flow<List<WorkoutPreview>> {
        return workoutDao.observeWorkoutsPreviews(
            sqlQuery = getWorkoutsRawQuery(
                query = query,
                workoutTypes = workoutsFilter.selectedWorkoutTypes,
                equipments = workoutsFilter.selectedEquipments,
                muscles = workoutsFilter.selectedMuscles,
                timeFilters = workoutsFilter.selectedTimeFilters,
                sort = workoutsSort.name,
                isPublic = null,
                isCustom = null,
                isFavorite = true,
                limit = null
            )
        ).map { workoutPreviewDbViews ->
            workoutPreviewDbViews.map { it.toWorkoutPreview() }
        }
    }

    override fun observeWorkout(id: String): Flow<Workout?> {
        return workoutDao.observeWorkoutWithDetailsByUUID(id)
            .map { it?.toWorkout() }
    }

    override suspend fun setFavorite(
        workoutId: String,
        isFavorite: Boolean
    ): EmptyResult<DataError> {
        workoutDao.setFavorite(
            workoutUUID = workoutId,
            isFavorite = isFavorite,
            isFavoriteChangedAt = Clock.System.now()
        )
        // TODO make a network request to set favorite later
        return AppResult.Success(Unit)
    }

    private fun getWorkoutsRawQuery(
        query: String,
        workoutTypes: List<WorkoutType>,
        equipments: List<Equipment>,
        muscles: List<Muscle>,
        timeFilters: List<WorkoutTimeFilter>,
        sort: String,
        isPublic: Boolean?,
        isFavorite: Boolean?,
        isCustom: Boolean?,
        limit: Int?,
    ): RoomRawQuery {
        val baseQuery = StringBuilder(
            """
            SELECT w.* FROM workouts w
            LEFT JOIN workout_muscles wm ON wm.workoutUUID = w.clientUUID
            LEFT JOIN workout_equipments we ON we.workoutUUID = w.clientUUID
            LEFT JOIN workout_types wt ON wt.workoutUUID = w.clientUUID
            LEFT JOIN user_profiles up ON up.id = w.authorId
        """.trimIndent()
        )

        // Track if any WHERE clause has been added
        var hasWhereClause = false

        fun appendCondition(condition: String) {
            baseQuery.append(if (hasWhereClause) " AND" else " WHERE").append(" ").append(condition)
            hasWhereClause = true
        }

        fun getFlagValue(boolean: Boolean): Int = if (boolean) 1 else 0

        isPublic?.let {
            val isPublicFlag = getFlagValue(it)
            appendCondition("w.isPublic = $isPublicFlag")
        }

        isFavorite?.let {
            val isFavoriteFlag = getFlagValue(it)
            appendCondition("w.isFavorite = $isFavoriteFlag")
        }

        isCustom?.let {
            val isCustomFlag = getFlagValue(it)
            appendCondition("w.isCreatedByCurrentUser = $isCustomFlag")
        }

        if (muscles.isNotEmpty()) {
            appendCondition("wm.muscle IN (${muscles.joinToString(",") { "'${it.name}'" }})")
        }

        if (equipments.isNotEmpty()) {
            appendCondition("we.equipment IN (${equipments.joinToString(",") { "'${it.name}'" }})")
        }

        if (workoutTypes.isNotEmpty()) {
            appendCondition("wt.workoutType IN (${workoutTypes.joinToString(",") { "'${it.name}'" }})")
        }

        if (timeFilters.isNotEmpty()) {
            val timeConditions = timeFilters.joinToString(" OR ") {
                when (it) {
                    WorkoutTimeFilter.LessThan30Min -> "w.durationMin BETWEEN 0 AND 30"
                    WorkoutTimeFilter.Between30And60Min -> "w.durationMin BETWEEN 30 AND 60"
                    WorkoutTimeFilter.Between60And90Min -> "w.durationMin BETWEEN 60 AND 90"
                    WorkoutTimeFilter.MoreThan90Min -> "w.durationMin > 90"
                }
            }
            appendCondition(timeConditions)
        }

        appendCondition(
            """
            (LOWER(w.name) LIKE '%' || LOWER('$query') || '%'
            OR LOWER(up.name) LIKE '%' || LOWER('$query') || '%')
            """.trimIndent()
        )

        baseQuery.append(
            """
            GROUP BY w.clientUUID
            ORDER BY 
            CASE WHEN '$sort' = 'Newest' THEN w.createdAt END DESC,
            CASE WHEN '$sort' = 'Oldest' THEN w.createdAt END ASC,
            CASE WHEN '$sort' = 'MostLiked' THEN w.favoritesCount END DESC,
            CASE WHEN '$sort' = 'LeastLiked' THEN w.favoritesCount END ASC
        """.trimIndent()
        )

        limit?.let { baseQuery.append(" LIMIT $it") }

        return RoomRawQuery(baseQuery.toString())
    }

}
