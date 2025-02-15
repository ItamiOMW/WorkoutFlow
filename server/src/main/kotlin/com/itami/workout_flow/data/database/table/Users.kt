package com.itami.workout_flow.data.database.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone
import java.time.OffsetDateTime

object Users : LongIdTable(name = "users") {
    val firebaseUID = varchar(name = "firebase_uid", length = 36).uniqueIndex().nullable()
    val email = varchar(name = "email", length = 320).uniqueIndex()
    val passwordHash = varchar(name = "passwordHash", length = 255).nullable()
    val name = varchar(name = "name", length = 100)
    val username = varchar(name = "username", length = 24).uniqueIndex()
    val profilePictureUrl = varchar(name = "profile_pic_url", length = 255).nullable().default(null)
    val isActive = bool("is_active")
    val createdAt = timestampWithTimeZone(name = "created_at").default(OffsetDateTime.now())
    val updatedAt = timestampWithTimeZone(name = "updated_at").nullable().default(null)
}