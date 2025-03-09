package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseEntity

@Entity(
    tableName = "workout_exercises",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["clientUUID"],
            childColumns = ["workoutUUID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SupersetEntity::class,
            parentColumns = ["clientUUID"],
            childColumns = ["supersetUUID"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("workoutUUID"), Index("supersetUUID"), Index("exerciseId")]
)
data class WorkoutExerciseEntity(
    @PrimaryKey
    val clientUUID: String,
    val serverId: Long? = null,
    val workoutUUID: String,
    val supersetUUID: String?,
    val exerciseId: Long,
    val order: Int,
)