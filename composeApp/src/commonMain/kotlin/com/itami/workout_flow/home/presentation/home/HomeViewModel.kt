package com.itami.workout_flow.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.workout_flow.core.domain.repository.AppSettings
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val appSettings: AppSettings,
): ViewModel() {

    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.EditRoutineClick -> {
                sendUiEvent(HomeEvent.NavigateToRoutine)
            }

            is HomeAction.HideSignInClick -> {
                viewModelScope.launch {
                    appSettings.setShowSignInOnHomeScreen(showSignIn = false)
                }
            }

            is HomeAction.ProfilePictureClick -> {
                sendUiEvent(HomeEvent.NavigateToProfile)
            }

            is HomeAction.SearchClick -> {
                sendUiEvent(HomeEvent.NavigateToSearch)
            }

            is HomeAction.SelectRoutineDay -> {

            }

            is HomeAction.SignInClick -> {
                sendUiEvent(HomeEvent.NavigateToSignIn)
            }

        }
    }

    private fun sendUiEvent(event: HomeEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

}
