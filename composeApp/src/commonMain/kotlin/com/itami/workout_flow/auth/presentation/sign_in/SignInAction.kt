package com.itami.workout_flow.auth.presentation.sign_in

sealed interface SignInAction {

    data object SignInWithGoogleClick : SignInAction

    data object SignInWithAppleClick : SignInAction

    data object NavigateBackClick : SignInAction

}