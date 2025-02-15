package com.itami.workout_flow.core.data.local.preferences.user

import kotlinx.serialization.Serializable

@Serializable
sealed class DataStoreCurrentUser {

    @Serializable
    data object Guest : DataStoreCurrentUser()

    @Serializable
    data class Authenticated(
        val id: Long,
        val name: String,
        val username: String,
        val email: String,
        val profilePictureUrl: String?,
        val subscription: DataStoreSubscription,
    ) : DataStoreCurrentUser()

}

@Serializable
sealed class DataStoreSubscription {

    @Serializable
    data object Basic : DataStoreSubscription()

    @Serializable
    data class Premium(val expirationDateTime: String) : DataStoreSubscription()

}