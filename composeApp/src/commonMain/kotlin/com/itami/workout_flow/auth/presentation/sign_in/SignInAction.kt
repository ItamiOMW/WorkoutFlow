package com.itami.workout_flow.auth.presentation.sign_in

import dev.gitlive.firebase.auth.FirebaseUser

sealed interface SignInAction {

    data class SignInResult(val firebaseUserResult: Result<FirebaseUser?>) : SignInAction

    data object NavigateBackClick : SignInAction

}