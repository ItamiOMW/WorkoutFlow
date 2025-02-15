package com.itami.workout_flow.core.data.local.preferences.user

import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.core.domain.model.user.CurrentUser
import kotlinx.coroutines.flow.Flow

interface CurrentUserPreferences {

    val user: Flow<CurrentUser>

    suspend fun setUser(user: CurrentUser): AppResult<Unit, DataError.Local>

}