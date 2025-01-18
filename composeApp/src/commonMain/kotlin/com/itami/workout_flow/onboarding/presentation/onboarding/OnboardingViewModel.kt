package com.itami.workout_flow.onboarding.presentation.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class OnboardingViewModel(

): ViewModel() {

    private val _events = Channel<OnboardingEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(OnboardingState())
    val state = _state.asStateFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            is OnboardingAction.NextClick -> {

            }
        }
    }

}



