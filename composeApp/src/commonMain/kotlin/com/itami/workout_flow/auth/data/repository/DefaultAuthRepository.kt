package com.itami.workout_flow.auth.data.repository

import com.itami.workout_flow.auth.data.mapper.toCurrentAuthenticatedUser
import com.itami.workout_flow.auth.data.remote.AuthApiService
import com.itami.workout_flow.auth.domain.repository.AuthRepository
import com.itami.workout_flow.core.data.local.preferences.settings.AppSettingsPreferences
import com.itami.workout_flow.core.data.local.preferences.user.CurrentUserPreferences
import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.core.domain.model.result.asEmptyDataResult
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class DefaultAuthRepository(
    private val authApiService: AuthApiService,
    private val currentUserPreferences: CurrentUserPreferences,
    private val appSettingsPreferences: AppSettingsPreferences,
) : AuthRepository {

    override suspend fun authenticate(): AppResult<Unit, DataError> {
        return when (val result = authApiService.authenticateWithFirebase()) {
            is AppResult.Success -> {
                val currentUser = result.data.toCurrentAuthenticatedUser()
                val setUserResult = currentUserPreferences.setUser(currentUser)
                if (setUserResult is AppResult.Success) {
                    appSettingsPreferences.setShowSignInOnHomeScreen(false)
                }
                setUserResult
            }

            is AppResult.Error -> {
                result.asEmptyDataResult()
            }
        }
    }

    override suspend fun signOut(): AppResult<Unit, DataError> {
        Firebase.auth.signOut()
        return AppResult.Success(Unit)
    }

}