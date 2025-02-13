package com.itami.workout_flow.auth.data.remote

import com.itami.workout_flow.core.data.remote.safeRequest
import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.dto.response.UserResponse
import com.itami.workout_flow.routes.AuthRoute
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get

class KtorAuthApiService(
    private val httpClient: HttpClient,
) : AuthApiService {

    override suspend fun authenticateWithFirebase(): AppResult<UserResponse, DataError.Remote> {
        return safeRequest {
            httpClient.get(AuthRoute.Firebase)
        }
    }

}