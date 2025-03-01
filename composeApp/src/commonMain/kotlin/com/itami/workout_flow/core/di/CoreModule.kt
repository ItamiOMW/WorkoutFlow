package com.itami.workout_flow.core.di

import com.itami.workout_flow.core.data.local.database.WorkoutFlowDatabase
import com.itami.workout_flow.core.data.local.database.dao.ExerciseDao
import com.itami.workout_flow.core.data.local.database.dao.UserProfileDao
import com.itami.workout_flow.core.data.local.database.dao.WorkoutDao
import com.itami.workout_flow.core.data.local.preferences.settings.AppSettingsPreferences
import com.itami.workout_flow.core.data.local.preferences.settings.DataStoreAppSettingsPreferences
import com.itami.workout_flow.core.data.local.preferences.user.CurrentUserPreferences
import com.itami.workout_flow.core.data.local.preferences.user.DataStoreCurrentUserPreferences
import com.itami.workout_flow.core.data.remote.utils.HttpClientFactory
import com.itami.workout_flow.core.data.remote.workouts.KtorWorkoutsApiService
import com.itami.workout_flow.core.data.remote.workouts.WorkoutsApiService
import com.itami.workout_flow.core.data.remote.workouts.WorkoutsRemoteMediator
import com.itami.workout_flow.core.data.repository.DefaultAppSettings
import com.itami.workout_flow.core.data.repository.DefaultUserRepository
import com.itami.workout_flow.core.data.repository.MockWorkoutRepository
import com.itami.workout_flow.core.domain.repository.AppSettings
import com.itami.workout_flow.core.domain.repository.UserRepository
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import io.ktor.client.HttpClient
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
    singleOf(::KtorWorkoutsApiService).bind<WorkoutsApiService>()

    single<WorkoutDao> { get<WorkoutFlowDatabase>().workoutDao }
    single<ExerciseDao> { get<WorkoutFlowDatabase>().exerciseDao }
    single<UserProfileDao> { get<WorkoutFlowDatabase>().userProfileDao }
    single<HttpClient> { HttpClientFactory.create(get()) }

    single {
        DataStoreAppSettingsPreferences(
            dataStorePreferences = get(qualifier = dataStorePreferencesQualifier)
        )
    }.bind<AppSettingsPreferences>()

    single {
        DataStoreCurrentUserPreferences(
            dataStore = get(qualifier = dataStoreCurrentUserQualifier)
        )
    }.bind<CurrentUserPreferences>()
}