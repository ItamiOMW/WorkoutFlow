package com.itami.workout_flow.core.data.local.database.entity.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itami.workout_flow.model.ExerciseType

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String?,
    val steps: List<String>,
    val exerciseGifUrl: String?,
    val exerciseType: ExerciseType,
)