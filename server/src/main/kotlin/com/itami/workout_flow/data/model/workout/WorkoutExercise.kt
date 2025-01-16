package com.itami.workout_flow.data.model.workout

data class WorkoutExercise(
    val serverId: Long,
    val clientUUID: String,
    val supersetId: Long?,
    val supersetUUID: String?,
    val exercise: Exercise,
    val sets: List<Set>,
    val order: Int,
)