package com.itami.workout_flow.data.database.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object Workouts : LongIdTable("workouts") {
    val clientUUID = uuid("client_uuid").uniqueIndex()
    val authorId = reference("user_id", Users.id)
    val name = varchar("name", 100)
    val description = varchar("description", 255)
    val durationMin = integer("duration")
    val isPublic = bool("is_public").default(false)
    val createdAt = timestampWithTimeZone("created_at")
    val updatedAt = timestampWithTimeZone("updated_at").nullable().default(null)
}