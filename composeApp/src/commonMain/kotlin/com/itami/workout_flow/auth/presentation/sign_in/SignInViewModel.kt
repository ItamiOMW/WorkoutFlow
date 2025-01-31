package com.itami.workout_flow.auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(

) : ViewModel() {

    private val _events = Channel<SignInEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onAction(action: SignInAction) {
        when (action) {
            SignInAction.NavigateBackClick -> {
                sendUiEvent(SignInEvent.NavigateBack)
            }

            SignInAction.SignInWithAppleClick -> {

            }

            SignInAction.SignInWithGoogleClick -> {

            }
        }
    }

    private fun sendUiEvent(event: SignInEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

}
