package com.itami.workout_flow.core.domain.model.user

data class CurrentUser(
    val id: Long,
    val email: String,
    val name: String,
    val username: String,
    val profilePictureUrl: String?,
    val subscription: Subscription,
) {
    sealed class Subscription {
        data object Basic : Subscription()
        data class Premium(val expirationDateTime: String) : Subscription()
    }
}
