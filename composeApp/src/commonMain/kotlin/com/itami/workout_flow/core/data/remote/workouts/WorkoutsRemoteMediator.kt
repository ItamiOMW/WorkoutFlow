package com.itami.workout_flow.core.data.remote.workouts

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import app.cash.paging.RemoteMediator
import com.itami.workout_flow.core.data.local.database.WorkoutFlowDatabase
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutPreviewDbView
import com.itami.workout_flow.core.data.mapper.toExerciseWithDetails
import com.itami.workout_flow.core.data.mapper.toUserProfileEntity
import com.itami.workout_flow.core.data.mapper.toWorkoutEntry
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.dto.response.ExerciseResponse
import com.itami.workout_flow.dto.response.UserProfileResponse
import com.itami.workout_flow.dto.response.WorkoutResponse
import com.itami.workout_flow.model.WorkoutsFilter
import com.itami.workout_flow.model.WorkoutsSort
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@OptIn(ExperimentalPagingApi::class)
class WorkoutsRemoteMediator(
    private val database: WorkoutFlowDatabase,
    private val workoutsApiService: WorkoutsApiService,
    private val pageSize: Int,
    private val query: String?,
    private val workoutsFilter: WorkoutsFilter,
    private val workoutsSort: WorkoutsSort,
) : RemoteMediator<Int, WorkoutPreviewDbView>() {

    private var page: Int = 1

    private var createdBeforeCursor: Instant = Clock.System.now()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WorkoutPreviewDbView>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    createdBeforeCursor = Clock.System.now()
                    page = 1
                    page
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    page++
                    page
                }
            }

            val response = workoutsApiService.getWorkouts(
                page = loadKey,
                pageSize = pageSize,
                query = query,
                createdBefore = createdBeforeCursor,
                workoutsFilter = workoutsFilter,
                workoutsSort = workoutsSort
            )

            return when (response) {
                is AppResult.Success -> {
                    saveData(
                        exercises = response.data.exercises,
                        authors = response.data.authors,
                        workouts = response.data.workouts
                    )
                    MediatorResult.Success(endOfPaginationReached = response.data.workouts.isEmpty())
                }

                is AppResult.Error -> {
                    MediatorResult.Error(response.error)
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun saveData(
        exercises: List<ExerciseResponse>,
        authors: List<UserProfileResponse>,
        workouts: List<WorkoutResponse>
    ) {
        val exercisesWithDetails = exercises
            .map { it.toExerciseWithDetails() }

        val userProfileEntities = authors
            .map { it.toUserProfileEntity() }

        val workoutEntries = workouts
            .map { it.toWorkoutEntry() }

        database.userProfileDao.insertAll(userProfileEntities)

        exercisesWithDetails.forEach { exerciseWithDetails ->
            database.exerciseDao.insertExerciseWithDetails(
               exerciseWithDetails
            )
        }

        workoutEntries.forEach { workoutEntry ->
            database.workoutDao.synchronizeWorkout(workoutEntry)
        }
    }

}