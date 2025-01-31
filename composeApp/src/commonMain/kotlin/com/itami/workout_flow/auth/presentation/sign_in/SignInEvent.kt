package com.itami.workout_flow.auth.presentation.sign_in

sealed interface SignInEvent {

    data object NavigateBack : SignInEvent

}