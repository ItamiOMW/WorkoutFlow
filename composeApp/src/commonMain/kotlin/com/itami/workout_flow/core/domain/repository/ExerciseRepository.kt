package com.itami.workout_flow.core.domain.repository

import app.cash.paging.PagingData
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.ExercisesFilter
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun searchExercise(
        query: String,
        exercisesFilter: ExercisesFilter,
    ): Flow<PagingData<Exercise>>

    suspend fun getExerciseById(exerciseId: Long): Exercise?

}