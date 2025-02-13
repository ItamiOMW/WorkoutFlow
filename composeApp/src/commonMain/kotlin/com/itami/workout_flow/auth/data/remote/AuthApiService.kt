package com.itami.workout_flow.auth.data.remote

import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.dto.response.UserResponse

interface AuthApiService {

    suspend fun authenticateWithFirebase(): AppResult<UserResponse, DataError.Remote>

}