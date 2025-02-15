package com.itami.workout_flow.core.data.repository

import com.itami.workout_flow.core.data.local.preferences.settings.AppSettingsPreferences
import com.itami.workout_flow.core.domain.model.settings.Settings
import com.itami.workout_flow.core.domain.model.settings.Theme
import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import com.itami.workout_flow.core.domain.repository.AppSettings
import kotlinx.coroutines.flow.Flow

class DefaultAppSettings(
    private val appSettingsPreferences: AppSettingsPreferences
) : AppSettings {

    override val settings: Flow<Settings>
        get() = appSettingsPreferences.settings

    override val theme: Flow<Theme>
        get() = appSettingsPreferences.theme

    override val showSignInOnHomeScreen: Flow<Boolean>
        get() = appSettingsPreferences.showSignInOnHomeScreen

    override val distanceUnit: Flow<DistanceUnit>
        get() = appSettingsPreferences.distanceUnit

    override val weightUnit: Flow<WeightUnit>
        get() = appSettingsPreferences.weightUnit

    override suspend fun setTheme(theme: Theme) {
        appSettingsPreferences.setTheme(theme)
    }

    override suspend fun setShowSignInOnHomeScreen(showSignIn: Boolean) {
        appSettingsPreferences.setShowSignInOnHomeScreen(showSignIn)
    }

    override suspend fun setWeightUnit(weightUnit: WeightUnit) {
        appSettingsPreferences.setWeightUnit(weightUnit)
    }

    override suspend fun setDistanceUnit(distanceUnit: DistanceUnit) {
        appSettingsPreferences.setDistanceUnit(distanceUnit)
    }

}