package com.itami.workout_flow.core.di

import com.itami.workout_flow.core.data.repository.DataStoreAppSettings
import com.itami.workout_flow.core.domain.repository.AppSettings
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    singleOf(::DataStoreAppSettings).bind<AppSettings>()
}