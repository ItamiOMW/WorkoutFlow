package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.itami.workout_flow.model.DayOfWeek
import kotlinx.datetime.Instant

@Entity(
    tableName = "scheduled_workouts",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["clientUUID"],
            childColumns = ["workoutUUID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["workoutUUID"])]
)
data class ScheduledWorkoutEntity(
    @PrimaryKey
    val uuid: String,
    val workoutUUID: String,
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val isSynced: Boolean = false,
    val updatedAt: Instant? = null
)