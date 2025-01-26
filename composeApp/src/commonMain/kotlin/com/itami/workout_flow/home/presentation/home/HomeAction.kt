package com.itami.workout_flow.home.presentation.home

sealed interface HomeAction {

    data object ProfilePictureClick : HomeAction

    data object SearchClick : HomeAction

    data object EditRoutineClick : HomeAction

    data object SignInClick : HomeAction

    data object HideSignInClick : HomeAction

    data class SelectRoutineDay(val routineDay: RoutineDayUI) : HomeAction

}