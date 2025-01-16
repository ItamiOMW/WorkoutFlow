package com.itami.workout_flow.core.domain.model.workout

import com.itami.workout_flow.core.domain.model.user.UserProfile
import com.itami.workout_flow.model.Muscle

data class WorkoutPreview(
    val id: String,
    val author: UserProfile?,
    val name: String,
    val durationMin: Int,
    val muscles: List<Muscle>,
    val favoritesCount: Int,
    val isFavorite: Boolean,
    val isCreatedByCurrentUser: Boolean,
)