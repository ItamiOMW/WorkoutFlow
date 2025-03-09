package com.itami.workout_flow.data.database.table

import org.jetbrains.exposed.dao.id.CompositeIdTable

object ExerciseSteps: CompositeIdTable("exercise_steps") {
    val exerciseId = reference("exercise_id", Exercises.id)
    val stepText = varchar("step_text", 255)
    val order = integer("order").default(1).entityId()

    init {
        addIdColumn(exerciseId)
    }

    override val primaryKey = PrimaryKey(exerciseId, order)
}