package com.itami.workout_flow.core.domain.model.user

data class UserProfile(
    val id: Long,
    val name: String,
    val username: String,
    val profilePictureUrl: String?,
    val subscription: Subscription,
) {
    enum class Subscription {
        BASIC, PREMIUM
    }
}
