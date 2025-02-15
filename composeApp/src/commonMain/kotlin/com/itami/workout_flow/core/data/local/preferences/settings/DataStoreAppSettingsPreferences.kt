package com.itami.workout_flow.core.data.local.preferences.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.itami.workout_flow.core.data.utils.handleDataStoreException
import com.itami.workout_flow.core.domain.model.settings.Settings
import com.itami.workout_flow.core.domain.model.settings.Theme
import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreAppSettingsPreferences(
    private val dataStorePreferences: DataStore<Preferences>
) : AppSettingsPreferences {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val WEIGHT_UNIT_KEY = stringPreferencesKey("weight_unit")
        val DISTANCE_UNIT_KEY = stringPreferencesKey("distance_unit")
        val SHOW_ONBOARDING_KEY = booleanPreferencesKey("show_onboarding")
        val SHOW_SIGN_IN_ON_HOME_SCREEN_KEY = booleanPreferencesKey("show_sign_in_on_home_screen")
    }

    override val settings: Flow<Settings>
        get() = dataStorePreferences.data
            .handleDataStoreException()
            .map { preferences ->
                val theme = preferences[THEME_KEY]?.let { str -> Theme.valueOf(str) } ?: Theme.SYSTEM_THEME
                val showOnboarding = preferences[SHOW_ONBOARDING_KEY] ?: true
                val showSignInOnHomeScreen = preferences[SHOW_SIGN_IN_ON_HOME_SCREEN_KEY] ?: true

                val distanceUnit = preferences[DISTANCE_UNIT_KEY]
                    ?.let { str -> DistanceUnit.valueOf(str) }
                    ?: DistanceUnit.KILOMETERS

                val weightUnit = preferences[WEIGHT_UNIT_KEY]
                    ?.let { str -> WeightUnit.valueOf(str) }
                    ?: WeightUnit.KILOGRAMS

                Settings(
                    theme = theme,
                    showOnboarding = showOnboarding,
                    showSignInOnHomeScreen = showSignInOnHomeScreen,
                    weightUnit = weightUnit,
                    distanceUnit = distanceUnit
                )
            }

    override val theme: Flow<Theme>
        get() = dataStorePreferences.data
            .handleDataStoreException()
            .map { preferences ->
                preferences[THEME_KEY]?.let { str -> Theme.valueOf(str) } ?: Theme.SYSTEM_THEME
            }

    override val showSignInOnHomeScreen: Flow<Boolean>
        get() = dataStorePreferences.data
            .handleDataStoreException()
            .map { preferences ->
                preferences[SHOW_SIGN_IN_ON_HOME_SCREEN_KEY] ?: true
            }

    override val distanceUnit: Flow<DistanceUnit>
        get() = dataStorePreferences.data
            .handleDataStoreException()
            .map { preferences ->
                preferences[DISTANCE_UNIT_KEY]
                    ?.let { str -> DistanceUnit.valueOf(str) }
                    ?: DistanceUnit.KILOMETERS
            }

    override val weightUnit: Flow<WeightUnit>
        get() = dataStorePreferences.data
            .handleDataStoreException()
            .map { preferences ->
                preferences[WEIGHT_UNIT_KEY]
                    ?.let { str -> WeightUnit.valueOf(str) }
                    ?: WeightUnit.KILOGRAMS
            }

    override suspend fun setTheme(theme: Theme) {
        dataStorePreferences.edit { preferences ->
            preferences[THEME_KEY] = theme.name
        }
    }

    override suspend fun setShowSignInOnHomeScreen(showSignIn: Boolean) {
        dataStorePreferences.edit { preferences ->
            preferences[SHOW_SIGN_IN_ON_HOME_SCREEN_KEY] = showSignIn
        }
    }

    override suspend fun setWeightUnit(weightUnit: WeightUnit) {
        dataStorePreferences.edit { preferences ->
            preferences[WEIGHT_UNIT_KEY] = weightUnit.name
        }
    }

    override suspend fun setDistanceUnit(distanceUnit: DistanceUnit) {
        dataStorePreferences.edit { preferences ->
            preferences[DISTANCE_UNIT_KEY] = distanceUnit.name
        }
    }

}