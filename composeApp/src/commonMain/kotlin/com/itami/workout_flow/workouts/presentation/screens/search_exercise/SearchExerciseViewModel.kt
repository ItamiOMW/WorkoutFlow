@file:OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)

package com.itami.workout_flow.workouts.presentation.screens.search_exercise

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.itami.workout_flow.core.domain.repository.ExerciseRepository
import com.itami.workout_flow.core.presentation.navigation.AppGraph.Workouts.SearchExerciseScreen.SearchExerciseLaunchMode
import com.itami.workout_flow.model.Exercise
import io.ktor.utils.io.locks.synchronized
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchExerciseViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _events = Channel<SearchExerciseEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(SearchExerciseState())
    val state = _state.asStateFlow()

    private val searchQueryFlow = _state
        .map { it.searchQuery }
        .distinctUntilChanged()
        .debounce(500)

    private val exercisesFilterFlow = _state
        .map { it.exercisesFilter }
        .distinctUntilChanged()

    val exercisesPagingData: Flow<PagingData<Exercise>> = combine(
        searchQueryFlow,
        exercisesFilterFlow
    ) { query, filter -> query to filter }
        .flatMapLatest { (query, filter) -> exerciseRepository.searchExercise(query, filter) }
        .cachedIn(viewModelScope)

    init {
        val launchMode = savedStateHandle.get<String>("launchMode")
            ?.let { launchModeStr -> SearchExerciseLaunchMode.valueOf(launchModeStr) }
            ?: SearchExerciseLaunchMode.Search

        _state.update { it.copy(launchMode = launchMode) }
    }

    fun onAction(action: SearchExerciseAction) {
        when (action) {
            is SearchExerciseAction.SearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.newValue) }
            }

            is SearchExerciseAction.ExerciseFilterChange -> {
                _state.update { it.copy(exercisesFilter = action.newExercisesFilter) }
            }

            SearchExerciseAction.ShowFilterSheet -> {
                _state.update { it.copy(showExerciseFilterSheet = true) }
            }

            SearchExerciseAction.HideFilterSheet -> {
                _state.update { it.copy(showExerciseFilterSheet = false) }
            }

            SearchExerciseAction.ShowSearchQuery -> {
                _state.update { it.copy(showSearchQuery = true) }
            }

            SearchExerciseAction.HideSearchQuery -> {
                _state.update { it.copy(showSearchQuery = false, searchQuery = "") }
            }

            SearchExerciseAction.NavigateBack -> {
                sendUiEvent(SearchExerciseEvent.NavigateBack)
            }

            is SearchExerciseAction.ExerciseClick -> {
                when (state.value.launchMode) {
                    SearchExerciseLaunchMode.Search -> {
                        sendUiEvent(SearchExerciseEvent.NavigateToExerciseDetails(action.exercise.id))
                    }

                    SearchExerciseLaunchMode.Select -> {
                        sendUiEvent(SearchExerciseEvent.NavigateBackWithResult(action.exercise))
                    }
                }
            }

            is SearchExerciseAction.ExerciseGifClick -> {
                sendUiEvent(SearchExerciseEvent.NavigateToExerciseDetails(exerciseId = action.exercise.id))
            }

            is SearchExerciseAction.ErrorOccurred -> {
                sendUiEvent(SearchExerciseEvent.ShowLocalSnackbar(action.errorMessage))
            }
        }
    }

    private fun sendUiEvent(event: SearchExerciseEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

}
