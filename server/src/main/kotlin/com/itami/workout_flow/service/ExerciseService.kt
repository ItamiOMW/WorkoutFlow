package com.itami.workout_flow.service

import com.itami.workout_flow.data.mapper.toExerciseResponse
import com.itami.workout_flow.data.repository.exercise.ExerciseRepository
import com.itami.workout_flow.dto.response.ExerciseResponse
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.Muscle

class ExerciseService(private val exerciseRepository: ExerciseRepository) {

    suspend fun getExercises(
        userId: Long?,
        page: Int?,
        pageSize: Int?,
        query: String?,
        muscles: List<Muscle>?,
        equipments: List<Equipment>?,
        exerciseTypes: List<ExerciseType>?
    ): List<ExerciseResponse> {
        return exerciseRepository.getExercises(
            userId = userId,
            query = query ?: "",
            page = page ?: 1,
            pageSize = pageSize ?: 20,
            exerciseTypes = exerciseTypes ?: emptyList(),
            equipments = equipments ?: emptyList(),
            muscles = muscles ?: emptyList()
        ).map { it.toExerciseResponse() }
    }

}