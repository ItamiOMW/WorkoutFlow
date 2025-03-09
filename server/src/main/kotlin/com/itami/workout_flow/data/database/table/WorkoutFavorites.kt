package com.itami.workout_flow.data.database.table

import org.jetbrains.exposed.dao.id.CompositeIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone
import java.time.OffsetDateTime

object WorkoutFavorites: CompositeIdTable("workout_favorites") {
    val workoutId = reference("workout_id", Workouts.id)
    val userId = reference("user_id", Users.id)
    val isFavorite = bool("is_favorite").default(true)
    val isFavoriteChangedAt = timestampWithTimeZone("created_at").default(OffsetDateTime.now())

    init {
        addIdColumn(workoutId)
        addIdColumn(userId)
    }

    override val primaryKey = PrimaryKey(workoutId, userId)
}