package com.itami.workout_flow.home.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.itami.workout_flow.home.presentation.home.HomeViewModel

val homeModule = module {
    viewModelOf(::HomeViewModel)
}