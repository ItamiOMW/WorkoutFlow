package com.itami.workout_flow.core.data.repository

import com.itami.workout_flow.core.data.local.preferences.user.CurrentUserPreferences
import com.itami.workout_flow.core.domain.model.user.CurrentUser
import com.itami.workout_flow.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class DefaultUserRepository(
    private val currentUserPreferences: CurrentUserPreferences
): UserRepository {

    override val currentUser: Flow<CurrentUser> = currentUserPreferences.user

}