package com.itami.workout_flow.core.data.local.database.entity.exercise

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "exercise_steps",
    primaryKeys = ["exerciseId", "order"],
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseStepEntity(
    val exerciseId: Long,
    val text: String,
    val order: Int
)
