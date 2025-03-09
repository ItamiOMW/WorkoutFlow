package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "supersets",
    foreignKeys = [
        ForeignKey(
            WorkoutEntity::class,
            parentColumns = ["clientUUID"],
            childColumns = ["workoutUUID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutUUID")]
)
data class SupersetEntity(
    @PrimaryKey
    val clientUUID: String,
    val serverId: Long? = null,
    val workoutUUID: String,
)