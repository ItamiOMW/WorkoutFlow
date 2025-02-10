package com.itami.workout_flow.dto.response

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
data class UserWithTokenResponse(
    val user: UserResponse,
    val accessToken: String,
    val refreshToken: String,
)

@Serializable
data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val username: String,
    val profilePictureUrl: String?,
    val subscription: SubscriptionResponse,
    val createdAt: String,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class SubscriptionResponse {

    @Serializable
    @SerialName("basic")
    data object Basic : SubscriptionResponse()

    @Serializable
    @SerialName("premium")
    data class Premium(val expirationDateTime: String): SubscriptionResponse()

}