package com.itami.workout_flow.core.data.remote.workouts

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import app.cash.paging.RemoteMediator
import com.itami.workout_flow.core.data.local.database.WorkoutFlowDatabase
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutEntity
import com.itami.workout_flow.core.data.mapper.toExerciseWithDetails
import com.itami.workout_flow.core.data.mapper.toUserProfileEntity
import com.itami.workout_flow.core.data.mapper.toWorkoutEntry
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.dto.response.ExerciseResponse
import com.itami.workout_flow.dto.response.UserProfileResponse
import com.itami.workout_flow.dto.response.WorkoutResponse
import com.itami.workout_flow.model.WorkoutsFilter
import com.itami.workout_flow.model.WorkoutsSort

@OptIn(ExperimentalPagingApi::class)
class WorkoutsRemoteMediator(
    private val database: WorkoutFlowDatabase,
    private val workoutsApiService: WorkoutsApiService,
    private val query: String?,
    private val workoutsFilter: WorkoutsFilter,
    private val workoutsSort: WorkoutsSort,
) : RemoteMediator<Long, WorkoutEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Long, WorkoutEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.serverId ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = workoutsApiService.getWorkouts(
                lastItemId = loadKey,
                pageSize = 10,
                query = query,
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
                exercise = exerciseWithDetails.exercise,
                exerciseSteps = exerciseWithDetails.steps,
                exerciseEquipments = exerciseWithDetails.equipments,
                exerciseMuscleInvolvements = exerciseWithDetails.muscleInvolvements
            )
        }

        workoutEntries.forEach { workoutEntry ->
            database.workoutDao.synchronizeWorkout(workoutEntry)
        }
    }

}