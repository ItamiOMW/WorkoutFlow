package com.itami.workout_flow.data.database.table

import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.MuscleRole
import org.jetbrains.exposed.dao.id.CompositeIdTable

object ExerciseMuscleInvolvements: CompositeIdTable("exercise_muscle_involvements") {
    val exerciseId = reference("exercise_id", Exercises.id)
    val muscle = enumeration<Muscle>("muscle").entityId()
    val muscleRole = enumeration<MuscleRole>("muscle_role").entityId()

    init {
        addIdColumn(exerciseId)
    }

    override val primaryKey = PrimaryKey(exerciseId, muscle, muscleRole)
}