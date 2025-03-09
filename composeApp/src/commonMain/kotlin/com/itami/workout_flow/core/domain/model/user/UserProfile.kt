package com.itami.workout_flow.core.domain.model.user

import com.itami.workout_flow.model.UserProfileSubscription

data class UserProfile(
    val id: Long,
    val name: String,
    val username: String,
    val profilePictureUrl: String?,
    val subscription: UserProfileSubscription,
)
