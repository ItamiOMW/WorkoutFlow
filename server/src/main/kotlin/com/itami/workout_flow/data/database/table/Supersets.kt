package com.itami.workout_flow.data.database.table

import org.jetbrains.exposed.dao.id.LongIdTable

object Supersets: LongIdTable("supersets") {
    val clientUUID = uuid("client_uuid").uniqueIndex()
    val workoutId = reference("workout_id", Workouts.id)
}