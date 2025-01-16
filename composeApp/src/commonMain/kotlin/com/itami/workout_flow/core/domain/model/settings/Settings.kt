package com.itami.workout_flow.core.domain.model.settings

import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit

data class Settings(
    val theme: Theme,
    val showOnboarding: Boolean,
    val showSignInOnHomeScreen: Boolean,
    val weightUnit: WeightUnit,
    val distanceUnit: DistanceUnit,
)