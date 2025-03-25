package com.itami.workout_flow.data.repository.exercise

import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.Muscle

interface ExerciseRepository {

    suspend fun getWorkouts(
        userId: Long?,
        query: String,
        page: Int,
        pageSize: Int,
        exerciseTypes: List<ExerciseType>,
        equipments: List<Equipment>,
        muscles: List<Muscle>,
    ): List<Exercise>

}