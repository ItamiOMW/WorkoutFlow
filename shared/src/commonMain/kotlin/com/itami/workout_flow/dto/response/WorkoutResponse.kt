package com.itami.workout_flow.dto.response

import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutType
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutResponse(
    val serverId: Long,
    val clientUUID: String,
    val authorId: Long,
    val name: String,
    val description: String,
    val durationMin: Int,
    val workoutTypes: List<WorkoutType>,
    val equipments: List<Equipment>,
    val muscles: List<Muscle>,
    val workoutExercises: List<WorkoutExerciseResponse>,
    val favoritesCount: Int,
    val isFavorite: Boolean,
    val isPublic: Boolean,
    val isCreatedByUser: Boolean,
    val createdAt: String,
    val updatedAt: String?,
    val isFavoriteChangedAt: String?,
)