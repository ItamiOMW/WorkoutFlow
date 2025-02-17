package com.itami.workout_flow.dto.response

import com.itami.workout_flow.model.UserProfileSubscription
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    val id: Long,
    val name: String,
    val username: String,
    val profilePictureUrl: String?,
    val subscription: UserProfileSubscription,
)