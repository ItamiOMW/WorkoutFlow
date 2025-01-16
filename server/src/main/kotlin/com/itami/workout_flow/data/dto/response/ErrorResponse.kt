package com.itami.workout_flow.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
)
