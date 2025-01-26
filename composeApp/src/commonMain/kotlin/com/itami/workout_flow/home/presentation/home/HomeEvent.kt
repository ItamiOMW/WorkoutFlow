package com.itami.workout_flow.home.presentation.home

sealed interface HomeEvent {

    data class ShowSnackbar(val message: String) : HomeEvent

    data object NavigateToRoutine : HomeEvent

    data object NavigateToSignIn : HomeEvent

    data object NavigateToProfile : HomeEvent

    data object NavigateToSearch : HomeEvent

}