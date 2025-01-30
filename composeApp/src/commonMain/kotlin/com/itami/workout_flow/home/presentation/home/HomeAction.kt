package com.itami.workout_flow.home.presentation.home

import com.itami.workout_flow.core.domain.model.workout.ScheduledWorkout
import com.itami.workout_flow.home.presentation.home.components.RoutineDayUI
import com.itami.workout_flow.model.WorkoutType

sealed interface HomeAction {

    data object ProfilePictureClick : HomeAction

    data object SearchBarClick : HomeAction

    data object EditRoutineClick : HomeAction

    data object SignInCardClick : HomeAction

    data object HideSignInCardClick : HomeAction

    data class RoutineDayClick(val routineDay: RoutineDayUI) : HomeAction

    data class ScheduledWorkoutClick(val scheduledWorkout: ScheduledWorkout) : HomeAction

    data class WorkoutTypeClick(val workoutType: WorkoutType) : HomeAction

}