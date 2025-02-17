package com.itami.workout_flow.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class SetResponse(
    val serverId: Long,
    val clientUUID: String,
    val reps: Int?,
    val weightGrams: Float?,
    val distanceMeters: Float?,
    val timeSeconds: Int?,
    val order: Int,
)