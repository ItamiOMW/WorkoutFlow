package com.itami.workout_flow.data.database.table

import org.jetbrains.exposed.dao.id.CompositeIdTable

object ExerciseFavorites: CompositeIdTable("exercise_favorites") {
    val exerciseId = reference("exercise_id", Exercises.id)
    val userId = reference("user_id", Users.id)

    init {
        addIdColumn(exerciseId)
        addIdColumn(userId)
    }

    override val primaryKey = PrimaryKey(exerciseId, userId)
}