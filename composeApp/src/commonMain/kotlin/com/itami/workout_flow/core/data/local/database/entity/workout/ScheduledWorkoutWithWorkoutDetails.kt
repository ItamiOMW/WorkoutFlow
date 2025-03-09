package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Embedded
import androidx.room.Relation

data class ScheduledWorkoutWithWorkoutDetails(
    @Embedded val scheduledWorkout: ScheduledWorkoutEntity,
    @Relation(
        entity = WorkoutEntity::class,
        parentColumn = "workoutUUID",
        entityColumn = "clientUUID"
    )
    val workoutPreviewDbView: WorkoutPreviewDbView
)