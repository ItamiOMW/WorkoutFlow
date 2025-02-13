package com.itami.workout_flow.app.di

import com.itami.workout_flow.data.repository.user.UserRepository
import com.itami.workout_flow.data.repository.user.UserRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
}