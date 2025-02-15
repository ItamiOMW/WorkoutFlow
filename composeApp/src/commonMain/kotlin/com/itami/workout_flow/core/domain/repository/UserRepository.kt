package com.itami.workout_flow.core.domain.repository

import com.itami.workout_flow.core.domain.model.user.CurrentUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val currentUser: Flow<CurrentUser>

}