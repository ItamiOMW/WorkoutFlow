package com.itami.workout_flow.auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.kmpauth.google.GoogleAuthProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    // DO NOT REMOVE: GoogleAuthProvider instance must be created before signing in with Google.
    private val googleAuthProvider: GoogleAuthProvider,
) : ViewModel() {

    private val _events = Channel<SignInEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.NavigateBackClick -> {
                sendUiEvent(SignInEvent.NavigateBack)
            }

            is SignInAction.SignInResult -> {
                val firebaseUser = action.firebaseUserResult.getOrNull()
                println("RESULT!!!!!!! ${firebaseUser?.email}")
            }
        }
    }

    private fun signIn() {

    }

    private fun sendUiEvent(event: SignInEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

}
