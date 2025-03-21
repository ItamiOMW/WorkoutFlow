package com.itami.workout_flow.core.domain.repository

import app.cash.paging.PagingData
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.Muscle
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun searchExercise(
        query: String,
        muscles: List<Muscle>,
        exerciseTypes: List<ExerciseType>,
        equipments: List<Equipment>
    ): Flow<PagingData<Exercise>>

}