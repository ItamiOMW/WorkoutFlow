package com.itami.workout_flow.core.data.local.database.entity.exercise

import androidx.room.Entity
import androidx.room.ForeignKey
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.MuscleRole

@Entity(
    tableName = "exercise_muscle_involvements",
    primaryKeys = ["exerciseId", "muscle", "muscleRole"],
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseMuscleInvolvementEntity(
    val exerciseId: Long,
    val muscle: Muscle,
    val muscleRole: MuscleRole,
)