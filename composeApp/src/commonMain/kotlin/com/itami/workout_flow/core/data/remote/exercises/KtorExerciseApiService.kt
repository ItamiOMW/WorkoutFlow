package com.itami.workout_flow.core.data.remote.exercises

import com.itami.workout_flow.core.data.remote.utils.safeRequest
import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.dto.response.ExerciseResponse
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.routes.ExercisesRoute
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get

class KtorExerciseApiService(
    private val httpClient: HttpClient,
) : ExercisesApiService {

    override suspend fun getExercises(
        page: Int,
        pageSize: Int,
        query: String?,
        muscles: List<Muscle>,
        exerciseTypes: List<ExerciseType>
    ): AppResult<List<ExerciseResponse>, DataError.Remote> {
        return safeRequest {
            httpClient.get(
                ExercisesRoute(
                    page = page,
                    pageSize = pageSize,
                    query = query,
                    muscles = muscles,
                    exerciseTypes = exerciseTypes,
                )
            )
        }
    }

}