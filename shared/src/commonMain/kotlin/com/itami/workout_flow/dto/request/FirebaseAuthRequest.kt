package com.itami.workout_flow.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseAuthRequest(
    val idToken: String,
)
