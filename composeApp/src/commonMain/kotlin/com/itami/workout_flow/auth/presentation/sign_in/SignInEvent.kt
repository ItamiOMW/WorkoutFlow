package com.itami.workout_flow.auth.presentation.sign_in

sealed interface SignInEvent {

    data object NavigateBack : SignInEvent

    data class ShowLocalSnackbar(val message: String) : SignInEvent

    data class ShowGlobalSnackbar(val message: String) : SignInEvent

}