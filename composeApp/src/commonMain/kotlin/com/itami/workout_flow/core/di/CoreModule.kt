package com.itami.workout_flow.core.di

import com.itami.workout_flow.core.data.remote.HttpClientFactory
import com.itami.workout_flow.core.data.repository.DataStoreAppSettings
import com.itami.workout_flow.core.data.repository.MockWorkoutRepository
import com.itami.workout_flow.core.domain.repository.AppSettings
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    singleOf(::MockWorkoutRepository).bind<WorkoutRepository>()
    singleOf(::DataStoreAppSettings).bind<AppSettings>()
    single { HttpClientFactory.create(get()) }
}