package com.itami.workout_flow.core.data.local.database.entity.exercise

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseWithDetails(
    @Embedded val exercise: ExerciseEntity,
    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    val steps: List<ExerciseStepEntity>,
    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    val equipments: List<ExerciseEquipmentEntity>,
    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    val muscleInvolvements: List<ExerciseMuscleInvolvementEntity>,
)