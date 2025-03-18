package com.itami.workout_flow.workouts.presentation.screens.workout_editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.workout_flow.core.domain.repository.AppSettings
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import com.itami.workout_flow.workouts.presentation.mapper.toWorkoutExerciseComponentsUI
import com.itami.workout_flow.workouts.presentation.model.SetUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseComponentUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutEditorViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val workoutRepository: WorkoutRepository,
    private val appSettings: AppSettings,
) : ViewModel() {

    private val _events = Channel<WorkoutEditorEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(WorkoutEditorState())
    val state = _state.asStateFlow()

    private var workoutId: String? = null

    init {
        initializeWorkout()
        observeWeightUnit()
        observeDistanceUnit()
    }

    fun onAction(action: WorkoutEditorAction) {
        when (action) {
            is WorkoutEditorAction.ChangeVisibleToOthers -> {
                _state.update {
                    it.copy(isVisibleToOthers = action.isVisible)
                }
            }

            is WorkoutEditorAction.ChangeWorkoutDesc -> {
                _state.update {
                    it.copy(workoutDesc = action.newValue)
                }
            }

            is WorkoutEditorAction.ChangeWorkoutName -> {
                _state.update {
                    it.copy(workoutName = action.newValue)
                }
            }

            is WorkoutEditorAction.ChangeDuration -> {
                _state.update {
                    it.copy(durationMin = action.durationMin)
                }
            }

            is WorkoutEditorAction.ChangeEquipment -> {
                _state.update {
                    it.copy(equipment = action.equipments)
                }
            }

            is WorkoutEditorAction.ChangeMuscles -> {
                _state.update {
                    it.copy(muscles = action.muscles)
                }
            }

            is WorkoutEditorAction.ChangeWorkoutTypes -> {
                _state.update {
                    it.copy(workoutTypes = action.types)
                }
            }

            is WorkoutEditorAction.ChangeWorkoutExerciseExpandedState -> {
                changeWorkoutExerciseExpandedState(
                    targetExerciseId = action.workoutExerciseId,
                    expanded = action.expanded
                )
            }

            is WorkoutEditorAction.ChangeSetValues -> {
                changeSetValues(
                    targetedWorkoutExerciseId = action.workoutExerciseId,
                    updatedSetUI = action.updatedSetUI
                )
            }

            is WorkoutEditorAction.AddSet -> {
                addSet(
                    targetedWorkoutExerciseId = action.workoutExerciseId
                )
            }

            is WorkoutEditorAction.SupersetWorkoutExerciseNavResult -> {

            }

            is WorkoutEditorAction.RemoveSet -> {
                removeSet(
                    targetedWorkoutExerciseId = action.workoutExerciseId,
                    setId = action.setId
                )
            }

            is WorkoutEditorAction.RemoveWorkoutExercise -> {
                removeWorkoutExercise(
                    targetWorkoutExerciseId = action.workoutExerciseId
                )
            }

            is WorkoutEditorAction.SaveWorkout -> {
                // SAVE WORKOUT
            }

            is WorkoutEditorAction.DeleteWorkout -> {
                _state.update {
                    it.copy(showDeleteWorkoutDialog = true)
                }
            }

            is WorkoutEditorAction.ConfirmDeleteWorkout -> {
                _state.update {
                    it.copy(showDeleteWorkoutDialog = false)
                }
                // DELETE WORKOUT
            }

            is WorkoutEditorAction.AddExercise -> {
                sendUiEvent(WorkoutEditorEvent.NavigateToSearchExercise)
            }

            is WorkoutEditorAction.WorkoutExerciseNavResult -> {

            }

            is WorkoutEditorAction.AddSupersetExercise -> {
                sendUiEvent(WorkoutEditorEvent.NavigateToSearchExercise)
            }

            is WorkoutEditorAction.NavigateBack -> {
                _state.update {
                    it.copy(showExitDialog = true)
                }
            }

            is WorkoutEditorAction.ConfirmNavigateBack -> {
                _state.update {
                    it.copy(showExitDialog = false)
                }
                sendUiEvent(WorkoutEditorEvent.NavigateBack)
            }

            is WorkoutEditorAction.OpenEditDurationSheet -> {
                _state.update {
                    it.copy(bottomSheetContent = WorkoutEditorState.BottomSheetContent.DURATION)
                }
            }

            is WorkoutEditorAction.OpenEditEquipmentSheet -> {
                _state.update {
                    it.copy(bottomSheetContent = WorkoutEditorState.BottomSheetContent.EQUIPMENT)
                }
            }

            is WorkoutEditorAction.OpenEditMusclesSheet -> {
                _state.update {
                    it.copy(bottomSheetContent = WorkoutEditorState.BottomSheetContent.MUSCLES)
                }
            }

            is WorkoutEditorAction.OpenEditWorkoutTypesSheet -> {
                _state.update {
                    it.copy(bottomSheetContent = WorkoutEditorState.BottomSheetContent.WORKOUT_TYPE)
                }
            }

            is WorkoutEditorAction.DismissNavigateBackDialog -> {
                _state.update {
                    it.copy(showExitDialog = false)
                }
            }

            is WorkoutEditorAction.DismissDeleteWorkoutDialog -> {
                _state.update {
                    it.copy(showDeleteWorkoutDialog = false)
                }
            }

            is WorkoutEditorAction.DismissBottomSheet -> {
                _state.update {
                    it.copy(bottomSheetContent = null)
                }
            }
        }
    }

    private fun sendUiEvent(event: WorkoutEditorEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

    private fun changeWorkoutExerciseExpandedState(
        targetExerciseId: String,
        expanded: Boolean,
    ) {
        fun changeExpandedInList(
            workoutExercises: List<WorkoutExerciseComponentUI>,
        ): List<WorkoutExerciseComponentUI> {
            return workoutExercises.map { workoutExercise ->
                if (workoutExercise.exerciseId == targetExerciseId) {
                    workoutExercise.copyComponent(expanded = expanded)
                } else {
                    workoutExercise
                }
            }
        }

        _state.update { currentState ->
            val workoutExercises = currentState.workoutExercises
            val updatedWorkoutExercises = changeExpandedInList(workoutExercises)
            currentState.copy(workoutExercises = updatedWorkoutExercises)
        }
    }

    private fun changeSetValues(
        targetedWorkoutExerciseId: String,
        updatedSetUI: SetUI,
    ) {

    }

    private fun addSet(targetedWorkoutExerciseId: String) {

    }

    private fun removeSet(
        targetedWorkoutExerciseId: String,
        setId: String,
    ) {

    }

    private fun removeWorkoutExercise(targetWorkoutExerciseId: String) {

    }

    private fun saveWorkout() {

    }

    private fun initializeWorkout() {
        viewModelScope.launch {
            savedStateHandle.get<String>("workoutId")?.let { passedWorkoutId ->
                workoutId = passedWorkoutId
                workoutRepository.getWorkout(id = passedWorkoutId)?.let { workout ->
                    _state.update {
                        WorkoutEditorState(
                            workoutName = workout.name,
                            workoutDesc = workout.description,
                            workoutTypes = workout.workoutTypes,
                            muscles = workout.muscles,
                            equipment = workout.equipments,
                            durationMin = workout.durationMin,
                            isVisibleToOthers = workout.isPublic,
                            workoutExercises = workout.workoutExercises.toWorkoutExerciseComponentsUI()
                        )
                    }
                }
            }
        }
    }

    private fun observeDistanceUnit() {
        viewModelScope.launch {
            appSettings.distanceUnit.collectLatest { distanceUnit ->
                _state.update {
                    it.copy(distanceUnit = distanceUnit)
                }
            }
        }
    }

    private fun observeWeightUnit() {
        viewModelScope.launch {
            appSettings.weightUnit.collectLatest { weightUnit ->
                _state.update {
                    it.copy(weightUnit = weightUnit)
                }
            }
        }
    }

}
