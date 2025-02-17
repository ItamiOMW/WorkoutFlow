package com.itami.workout_flow.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutExerciseResponse(
    val serverId: Long,
    val clientUUID: String,
    val supersetServerId: Long?,
    val supersetClientUUID: String?,
    val exerciseId: Long,
    val sets: List<SetResponse>,
    val order: Int,
)