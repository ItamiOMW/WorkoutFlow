package com.itami.workout_flow.workouts.di

import com.itami.workout_flow.workouts.presentation.screens.search_exercise.SearchExerciseViewModel
import com.itami.workout_flow.workouts.presentation.screens.workout_details.WorkoutDetailsViewModel
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.WorkoutEditorViewModel
import com.itami.workout_flow.workouts.presentation.screens.workouts.WorkoutsViewModel
import com.itami.workout_flow.workouts.presentation.view_model.SharedExerciseViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val workoutsModule = module {
    viewModelOf(::WorkoutsViewModel)
    viewModelOf(::WorkoutDetailsViewModel)
    viewModelOf(::WorkoutEditorViewModel)
    viewModelOf(::SearchExerciseViewModel)
    viewModelOf(::SharedExerciseViewModel)
}