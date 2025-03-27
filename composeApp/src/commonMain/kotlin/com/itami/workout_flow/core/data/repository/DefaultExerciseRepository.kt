package com.itami.workout_flow.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.room.RoomRawQuery
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.map
import com.itami.workout_flow.core.data.local.database.WorkoutFlowDatabase
import com.itami.workout_flow.core.data.mapper.toExercise
import com.itami.workout_flow.core.data.remote.exercises.ExerciseRemoteMediator
import com.itami.workout_flow.core.data.remote.exercises.ExercisesApiService
import com.itami.workout_flow.core.domain.repository.ExerciseRepository
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.ExercisesFilter
import com.itami.workout_flow.model.Muscle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultExerciseRepository(
    private val database: WorkoutFlowDatabase,
    private val exercisesApiService: ExercisesApiService,
) : ExerciseRepository {

    private val exerciseDao = database.exerciseDao

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun searchExercise(
        query: String,
        exercisesFilter: ExercisesFilter
    ): Flow<PagingData<Exercise>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
            ),
            initialKey = 1,
            pagingSourceFactory = {
                exerciseDao.getExercisesWithDetailsPagingSource(
                    sqlQuery = getExercisesRawQuery(
                        query = query,
                        exerciseTypes = exercisesFilter.selectedExerciseTypes,
                        equipments = exercisesFilter.selectedEquipments,
                        muscles = exercisesFilter.selectedMuscles,
                        limit = null
                    )
                )
            },
            remoteMediator = ExerciseRemoteMediator(
                database = database,
                exercisesApiService = exercisesApiService,
                pageSize = 20,
                query = query,
                exercisesFilter = exercisesFilter
            )
        ).flow.map { pagingData ->
            pagingData.map { it.toExercise() }
        }
    }

    override suspend fun getExerciseById(exerciseId: Long): Exercise? {
        return exerciseDao.getExerciseWithDetailsById(exerciseId)?.toExercise()
    }

    private fun getExercisesRawQuery(
        query: String,
        exerciseTypes: List<ExerciseType>,
        equipments: List<Equipment>,
        muscles: List<Muscle>,
        limit: Int?,
    ): RoomRawQuery {
        val baseQuery = StringBuilder(
            """
            SELECT DISTINCT e.* FROM exercises e
            LEFT JOIN exercise_steps es ON es.exerciseId = e.id
            LEFT JOIN exercise_equipments ee ON ee.exerciseId = e.id
            LEFT JOIN exercise_muscle_involvements emi ON emi.exerciseId = e.id
        """.trimIndent()
        )

        // Track if any WHERE clause has been added
        var hasWhereClause = false

        fun appendCondition(condition: String) {
            baseQuery.append(if (hasWhereClause) " AND" else " WHERE").append(" ").append(condition)
            hasWhereClause = true
        }

        if (muscles.isNotEmpty()) {
            appendCondition("emi.muscle IN (${muscles.joinToString(",") { "'${it.name}'" }})")
        }

        if (equipments.isNotEmpty()) {
            appendCondition("ee.equipment IN (${equipments.joinToString(",") { "'${it.name}'" }})")
        }

        if (exerciseTypes.isNotEmpty()) {
            appendCondition("e.exerciseType IN (${exerciseTypes.joinToString(",") { "'${it.name}'" }})")
        }

        appendCondition(
            """
            LOWER(e.name) LIKE '%' || LOWER('$query') || '%'
            """.trimIndent()
        )

        limit?.let { baseQuery.append(" LIMIT $it") }

        return RoomRawQuery(baseQuery.toString())
    }

}