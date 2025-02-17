package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Embedded
import androidx.room.Relation
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseEntity
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseWithDetails

data class WorkoutExerciseWithDetails(
    @Embedded val workoutExercise: WorkoutExerciseEntity,
    @Relation(
        entity = ExerciseEntity::class,
        parentColumn = "exerciseId",
        entityColumn = "id"
    )
    val exerciseWithDetails: ExerciseWithDetails,
    @Relation(parentColumn = "supersetUUID", entityColumn = "clientUUID")
    val superset: SupersetEntity?,
    @Relation(
        parentColumn = "clientUUID",
        entityColumn = "workoutExerciseUUID",
    )
    val sets: List<SetEntity>,
)