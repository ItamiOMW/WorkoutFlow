package com.itami.workout_flow.core.domain.repository

import app.cash.paging.PagingData
import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.EmptyResult
import com.itami.workout_flow.core.domain.model.workout.ScheduledWorkout
import com.itami.workout_flow.core.domain.model.workout.Workout
import com.itami.workout_flow.core.domain.model.workout.WorkoutPreview
import com.itami.workout_flow.model.WorkoutsFilter
import com.itami.workout_flow.model.WorkoutsSort
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    fun observeScheduledWorkouts(): Flow<List<ScheduledWorkout>>

    fun observePagingWorkoutPreviews(
        query: String,
        workoutsSort: WorkoutsSort,
        workoutsFilter: WorkoutsFilter,
    ): Flow<PagingData<WorkoutPreview>>

    fun observeCustomWorkoutPreviews(
        query: String,
        workoutsSort: WorkoutsSort,
        workoutsFilter: WorkoutsFilter,
    ): Flow<List<WorkoutPreview>>

    fun observeFavoriteWorkoutPreviews(
        query: String,
        workoutsSort: WorkoutsSort,
        workoutsFilter: WorkoutsFilter,
    ): Flow<List<WorkoutPreview>>

    fun observeWorkout(id: String): Flow<Workout?>

    suspend fun getWorkout(id: String): Workout?

    suspend fun setFavorite(
        workoutId: String,
        isFavorite: Boolean
    ): EmptyResult<DataError>

}