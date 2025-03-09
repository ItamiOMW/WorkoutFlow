package com.itami.workout_flow.data.database.table

import com.itami.workout_flow.model.Muscle
import org.jetbrains.exposed.dao.id.CompositeIdTable

object WorkoutMuscles: CompositeIdTable("workout_muscles") {
    val workoutId = reference("workout_id", Workouts.id)
    val muscle = enumeration<Muscle>("muscle").entityId()

    init {
        addIdColumn(workoutId)
    }

    override val primaryKey = PrimaryKey(workoutId, muscle)
}