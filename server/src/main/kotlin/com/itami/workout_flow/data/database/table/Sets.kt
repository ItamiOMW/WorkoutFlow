package com.itami.workout_flow.data.database.table

import org.jetbrains.exposed.dao.id.LongIdTable

object Sets: LongIdTable("sets") {
    val clientUUID = uuid("client_uuid").uniqueIndex()
    val workoutExerciseId = reference("workout_exercise_id", WorkoutExercises.id)
    val reps = integer("reps").nullable()
    val weightGrams = float("weight_grams").nullable()
    val distanceMeters = float("distance_meters").nullable()
    val timeMillis = integer("time_seconds").nullable()
    val order = integer("order")
}