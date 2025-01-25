package com.itami.workout_flow.core.domain.repository

import com.itami.workout_flow.core.domain.model.settings.Settings
import com.itami.workout_flow.core.domain.model.settings.Theme
import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import kotlinx.coroutines.flow.Flow

interface AppSettings {

    val settings: Flow<Settings>

    val theme: Flow<Theme>

    val showSignInOnHomeScreen: Flow<Boolean>

    val distanceUnit: Flow<DistanceUnit>

    val weightUnit: Flow<WeightUnit>

    suspend fun setTheme(theme: Theme)

    suspend fun setShowSignInOnHomeScreen(showSignIn: Boolean)

    suspend fun setWeightUnit(weightUnit: WeightUnit)

    suspend fun setDistanceUnit(distanceUnit: DistanceUnit)

}