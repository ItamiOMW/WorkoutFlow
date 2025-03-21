package com.itami.workout_flow.workouts.presentation.screens.search_exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SearchExerciseViewModel(

): ViewModel() {

    private val _events = Channel<SearchExerciseEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(SearchExerciseState())
    val state = _state.asStateFlow()

    fun onAction(action: SearchExerciseAction) {
        when (action) {
            SearchExerciseAction.NavigateBack -> {
                sendUiEvent(SearchExerciseEvent.NavigateBack)
            }
        }
    }

    private fun sendUiEvent(event: SearchExerciseEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

}
