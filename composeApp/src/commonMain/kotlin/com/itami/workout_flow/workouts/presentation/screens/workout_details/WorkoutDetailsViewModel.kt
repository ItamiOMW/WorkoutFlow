package com.itami.workout_flow.workouts.presentation.screens.workout_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.workout_flow.core.domain.repository.AppSettings
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import com.itami.workout_flow.workouts.presentation.mapper.toWorkoutUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutDetailsViewModel(
    private val workoutRepository: WorkoutRepository,
    private val appSettings: AppSettings,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _events = Channel<WorkoutDetailsEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow<WorkoutDetailsState>(WorkoutDetailsState.Initializing)
    val state = _state.asStateFlow()

    private lateinit var workoutId: String

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>("workoutId")?.let { id ->
                workoutId = id
                observeWorkoutDetails(workoutId = workoutId)
            } ?: throw RuntimeException("Workout id is required")
        }
    }

    fun onAction(action: WorkoutDetailsAction) {
        when (action) {
            WorkoutDetailsAction.NavigateBackClick -> {
                sendUiEvent(WorkoutDetailsEvent.NavigateBack)
            }

            WorkoutDetailsAction.FavoriteClick -> {
                toggleFavorite()
            }

            WorkoutDetailsAction.AuthorProfileImageClick -> {
                navigateToAuthorProfile()
            }

            WorkoutDetailsAction.MoreOptionsClick -> {
                toggleDropdown(expanded = true)
            }

            WorkoutDetailsAction.DismissDropdownRequest -> {
                _state.update { currentState ->
                    if (currentState is WorkoutDetailsState.Workout) {
                        currentState.copy(dropdownExpanded = false)
                    } else {
                        currentState
                    }
                }
            }

            WorkoutDetailsAction.AuthorProfileDropdownItemClick -> {
                navigateToAuthorProfile()
            }

            WorkoutDetailsAction.EditWorkoutDropdownItemClick -> {
                sendUiEvent(WorkoutDetailsEvent.NavigateToEditWorkout(workoutId = workoutId))
            }

            WorkoutDetailsAction.StartWorkoutDropdownItemClick -> {
                // Navigate to start workout
            }

            WorkoutDetailsAction.StartWorkoutFABClick -> {
                // Navigate to start workout
            }

            is WorkoutDetailsAction.WorkoutExerciseExpandedStateChange -> {
                changeExerciseExpandedState(
                    exerciseId = action.workoutExerciseId,
                    expanded = action.expanded,
                )
            }
        }
    }

    private fun sendUiEvent(event: WorkoutDetailsEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

    private fun navigateToAuthorProfile() {
        val currentState = state.value
        if (currentState is WorkoutDetailsState.Workout) {
            currentState.workoutUI.author?.let { author ->
                sendUiEvent(WorkoutDetailsEvent.NavigateToUserProfile(userId = author.id))
            }
        }
    }

    private fun changeExerciseExpandedState(
        exerciseId: String,
        expanded: Boolean,
    ) {
        _state.update { currentState ->
            if (currentState is WorkoutDetailsState.Workout) {
                val workoutUI = currentState.workoutUI
                val updatedWorkoutExercises = workoutUI.workoutExercises
                    .map { workoutExercise ->
                        if (exerciseId == workoutExercise.exerciseId) {
                            workoutExercise.copyComponent(expanded = expanded)
                        } else {
                            workoutExercise
                        }
                    }

                currentState.copy(
                    workoutUI = workoutUI.copy(workoutExercises = updatedWorkoutExercises)
                )
            } else {
                currentState
            }

        }
    }

    private fun toggleFavorite() {
        val currentState = state.value
        if (currentState is WorkoutDetailsState.Workout) {
            viewModelScope.launch {
                workoutRepository.setFavorite(
                    workoutId = workoutId,
                    isFavorite = !currentState.workoutUI.isFavorite
                )
            }
        }
    }

    private fun toggleDropdown(expanded: Boolean) {
        _state.update { currentState ->
            if (currentState is WorkoutDetailsState.Workout) {
                currentState.copy(dropdownExpanded = expanded)
            } else {
                currentState
            }
        }
    }

    private fun observeWorkoutDetails(workoutId: String) {
        viewModelScope.launch {
            combine(
                workoutRepository.observeWorkout(workoutId),
                appSettings.weightUnit,
                appSettings.distanceUnit
            ) { workout, weightUnit, distanceUnit ->
                Triple(workout, weightUnit, distanceUnit)
            }.collectLatest { (workout, weightUnit, distanceUnit) ->
                _state.update { currentState ->
                    workout?.let {
                        val previousWorkoutState = currentState as? WorkoutDetailsState.Workout
                        val previousDropdownExpanded = previousWorkoutState?.dropdownExpanded
                        val previousWorkoutExercises =
                            previousWorkoutState?.workoutUI?.workoutExercises.orEmpty()

                        val workoutUI = workout.toWorkoutUI()

                        val updatedWorkoutExercises = workoutUI.workoutExercises.map { exercise ->
                            val isExpanded = previousWorkoutExercises
                                .find { it == exercise }
                                ?.expanded ?: false

                            exercise.copyComponent(expanded = isExpanded)
                        }

                        WorkoutDetailsState.Workout(
                            workoutUI = workoutUI.copy(workoutExercises = updatedWorkoutExercises),
                            weightUnit = weightUnit,
                            distanceUnit = distanceUnit,
                            dropdownExpanded = previousDropdownExpanded ?: false
                        )
                    } ?: WorkoutDetailsState.Empty
                }
            }
        }
    }

}
