package com.itami.workout_flow.data.model.workout

import java.time.ZonedDateTime

data class WorkoutExerciseLog(
    val serverId: Long,
    val clientUUID: String,
    val userId: Long,
    val workoutLogServerId: Long?,
    val workoutLogClientUUID: String?,
    val supersetServerId: Long?,
    val supersetClientUUID: String?,
    val exercise: Exercise,
    val setLogs: List<SetLog>,
    val order: Int,
    val createdAt: ZonedDateTime,
)