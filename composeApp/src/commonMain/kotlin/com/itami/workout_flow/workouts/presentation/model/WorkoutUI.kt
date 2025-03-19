package com.itami.workout_flow.workouts.presentation.model

import com.itami.workout_flow.core.domain.model.user.UserProfile
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutType
import kotlinx.datetime.Instant

data class WorkoutUI(
    val id: String,
    val author: UserProfile?,
    val name: String,
    val description: String,
    val durationMin: Int,
    val workoutTypes: List<WorkoutType>,
    val equipments: List<Equipment>,
    val muscles: List<Muscle>,
    val workoutExercises: List<WorkoutExerciseComponentUI>,
    val favoritesCount: Int,
    val isFavorite: Boolean,
    val isPublic: Boolean,
    val isCreatedByCurrentUser: Boolean,
    val createdAt: Instant,
)