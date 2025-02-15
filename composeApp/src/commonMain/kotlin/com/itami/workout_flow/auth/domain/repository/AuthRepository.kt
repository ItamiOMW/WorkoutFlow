package com.itami.workout_flow.auth.domain.repository

import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult

interface AuthRepository {

    suspend fun authenticate(): AppResult<Unit, DataError>

    suspend fun signOut(): AppResult<Unit, DataError>

}