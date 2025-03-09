package com.itami.workout_flow.data.database.table

import org.jetbrains.exposed.dao.id.LongIdTable

object WorkoutExercises : LongIdTable("workout_exercises") {
    val clientUUID = uuid("client_uuid").uniqueIndex()
    val workoutId = reference("workout_id", Workouts.id)
    val exerciseId = reference("exercise_id", Exercises.id)
    val supersetId = reference("superset_id", Supersets.id).nullable()
    val order = integer("order")
}