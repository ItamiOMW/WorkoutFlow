package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import com.itami.workout_flow.model.Muscle

@Entity(
    tableName = "workout_muscles",
    primaryKeys = ["workoutUUID", "muscle"],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["clientUUID"],
            childColumns = ["workoutUUID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutMuscleEntity(
    val workoutUUID: String,
    val muscle: Muscle,
)