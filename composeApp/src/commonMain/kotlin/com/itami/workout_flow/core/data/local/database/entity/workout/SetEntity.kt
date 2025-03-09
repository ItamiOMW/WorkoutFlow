package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sets",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutExerciseEntity::class,
            parentColumns = ["clientUUID"],
            childColumns = ["workoutExerciseUUID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutExerciseUUID")]
)
data class SetEntity(
    @PrimaryKey
    val clientUUID: String,
    val serverId: Long? = null,
    val workoutExerciseUUID: String,
    val reps: Int?,
    val weightGrams: Float?,
    val distanceMeters: Float?,
    val timeSeconds: Int?,
    val order: Int,
)