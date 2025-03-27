package com.itami.workout_flow.app.di

import com.itami.workout_flow.data.repository.exercise.ExerciseRepository
import com.itami.workout_flow.data.repository.exercise.ExerciseRepositoryImpl
import com.itami.workout_flow.data.repository.user.UserRepository
import com.itami.workout_flow.data.repository.user.UserRepositoryImpl
import com.itami.workout_flow.data.repository.workout.WorkoutRepository
import com.itami.workout_flow.data.repository.workout.WorkoutRepositoryImpl
import com.itami.workout_flow.service.AuthService
import com.itami.workout_flow.service.ExerciseService
import com.itami.workout_flow.service.WorkoutService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    // Services
    singleOf(::AuthService)
    singleOf(::WorkoutService)
    singleOf(::ExerciseService)

    // Repositories
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::WorkoutRepositoryImpl).bind<WorkoutRepository>()
    singleOf(::ExerciseRepositoryImpl).bind<ExerciseRepository>()
}