package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import com.itami.workout_flow.model.WorkoutType

@Entity(
    tableName = "workout_types",
    primaryKeys = ["workoutUUID", "workoutType"],
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
data class WorkoutTypeEntity(
    val workoutUUID: String,
    val workoutType: WorkoutType,
)