package com.itami.workout_flow.core.data.local.database.entity.workout;

import androidx.room.Embedded
import androidx.room.Relation
import com.itami.workout_flow.core.data.local.database.entity.user.UserProfileEntity

data class WorkoutWithDetails(
    @Embedded val workout: WorkoutEntity,
    @Relation(
        entity = WorkoutTypeEntity::class,
        parentColumn = "clientUUID",
        entityColumn = "workoutUUID"
    )
    val workoutTypes: List<WorkoutTypeEntity>,
    @Relation(
        entity = WorkoutMuscleEntity::class,
        parentColumn = "clientUUID",
        entityColumn = "workoutUUID"
    )
    val workoutMuscles: List<WorkoutMuscleEntity>,
    @Relation(
        entity = WorkoutEquipmentEntity::class,
        parentColumn = "clientUUID",
        entityColumn = "workoutUUID"
    )
    val workoutEquipments: List<WorkoutEquipmentEntity>,
    @Relation(
        entity = WorkoutExerciseEntity::class,
        parentColumn = "clientUUID",
        entityColumn = "workoutUUID",
    )
    val workoutExerciseDetails: List<WorkoutExerciseWithDetails>,
    @Relation(
        entity = UserProfileEntity::class,
        parentColumn = "authorId",
        entityColumn = "id"
    )
    val author: UserProfileEntity?,
)