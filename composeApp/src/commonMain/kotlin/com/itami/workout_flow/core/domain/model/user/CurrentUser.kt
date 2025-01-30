package com.itami.workout_flow.core.domain.model.user

sealed class CurrentUser {

    data object Guest : CurrentUser()

    data class Authenticated(
        val id: Long,
        val name: String,
        val username: String,
        val email: String,
        val profilePictureUrl: String?,
        val subscription: Subscription,
    ) : CurrentUser()

    sealed class Subscription {
        data object Basic : Subscription()
        data class Premium(val expirationDateTime: String) : Subscription()
    }

}
