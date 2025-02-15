package com.itami.workout_flow.core.di

import com.itami.workout_flow.core.data.local.preferences.settings.AppSettingsPreferences
import com.itami.workout_flow.core.data.local.preferences.settings.DataStoreAppSettingsPreferences
import com.itami.workout_flow.core.data.local.preferences.user.CurrentUserPreferences
import com.itami.workout_flow.core.data.local.preferences.user.DataStoreCurrentUserPreferences
import com.itami.workout_flow.core.data.remote.HttpClientFactory
import com.itami.workout_flow.core.data.repository.DefaultAppSettings
import com.itami.workout_flow.core.data.repository.DefaultUserRepository
import com.itami.workout_flow.core.data.repository.MockWorkoutRepository
import com.itami.workout_flow.core.domain.repository.AppSettings
import com.itami.workout_flow.core.domain.repository.UserRepository
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val dataStorePreferencesQualifier = named("ds_preferences")
val dataStoreCurrentUserQualifier = named("ds_current_user")

val coreModule = module {
    singleOf(::MockWorkoutRepository).bind<WorkoutRepository>()
    singleOf(::DefaultUserRepository).bind<UserRepository>()
    singleOf(::DefaultAppSettings).bind<AppSettings>()

    single {
        DataStoreAppSettingsPreferences(dataStorePreferences = get(qualifier = dataStorePreferencesQualifier))
    }.bind<AppSettingsPreferences>()

    single {
        DataStoreCurrentUserPreferences(dataStore = get(qualifier = dataStoreCurrentUserQualifier))
    }.bind<CurrentUserPreferences>()

    single { HttpClientFactory.create(get()) }
}