package com.itami.workout_flow.core.data.remote.exercises

import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.dto.response.ExerciseResponse
import com.itami.workout_flow.model.ExercisesFilter

interface ExercisesApiService {

    suspend fun getExercises(
        page: Int,
        pageSize: Int,
        query: String?,
        exerciseFilter: ExercisesFilter,
    ): AppResult<List<ExerciseResponse>, DataError.Remote>

}