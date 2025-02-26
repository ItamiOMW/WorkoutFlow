package com.itami.workout_flow.data.database.table

import com.itami.workout_flow.model.WorkoutType
import org.jetbrains.exposed.dao.id.CompositeIdTable

object WorkoutTypes: CompositeIdTable("workout_types") {
    val workoutId = reference("workout_id", Workouts.id)
    val workoutType = enumeration<WorkoutType>("workout_type").entityId()

    init {
        addIdColumn(workoutId)
    }

    override val primaryKey = PrimaryKey(workoutId, workoutType)
}