package com.itami.workout_flow.workouts.presentation.screens.workout_editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.workout_flow.core.domain.repository.AppSettings
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import com.itami.workout_flow.workouts.presentation.mapper.toWorkoutExerciseComponentsUI
import com.itami.workout_flow.workouts.presentation.model.SetUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseComponentUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
                addSet(targetedWorkoutExerciseId = action.workoutExerciseId)
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

            is WorkoutEditorAction.DetachFromSuperset -> {
                detachFromSuperset(workoutExerciseId = action.workoutExerciseId)
            }

            is WorkoutEditorAction.TurnIntoSuperset -> {
                turnIntoSuperset(workoutExerciseId = action.workoutExerciseId)
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
                if (workoutExercise.workoutExerciseId == targetExerciseId) {
                    workoutExercise.copyComponent(expanded = expanded)
                } else {
                    workoutExercise
                }
            }
        }

        _state.update { currentState ->
            val workoutExercises = currentState.workoutExerciseComponents
            val updatedWorkoutExercises = changeExpandedInList(workoutExercises)
            currentState.copy(workoutExerciseComponents = updatedWorkoutExercises)
        }
    }

    private fun changeSetValues(
        targetedWorkoutExerciseId: String,
        updatedSetUI: SetUI,
    ) {
        fun List<SetUI>.updateSet(updatedSet: SetUI): List<SetUI> {
            return map { if (it.id == updatedSet.id) updatedSet else it }
        }

        fun updateWorkoutExerciseSets(workoutExercise: WorkoutExerciseUI): WorkoutExerciseUI {
            return workoutExercise.copy(sets = workoutExercise.sets.updateSet(updatedSetUI))
        }

        _state.update { currentState ->
            val updatedWorkoutExerciseComponents =
                currentState.workoutExerciseComponents.map { component ->
                    when (component) {
                        is WorkoutExerciseComponentUI.Single -> {
                            if (component.workoutExerciseId == targetedWorkoutExerciseId) {
                                component.copy(workoutExercise = updateWorkoutExerciseSets(component.workoutExercise))
                            } else {
                                component
                            }
                        }

                        is WorkoutExerciseComponentUI.Superset -> {
                            val workoutExercises =
                                component.workoutExercises.map { workoutExercise ->
                                    if (workoutExercise.id == targetedWorkoutExerciseId) {
                                        updateWorkoutExerciseSets(workoutExercise)
                                    } else {
                                        workoutExercise
                                    }
                                }
                            component.copy(workoutExercises = workoutExercises)
                        }

                    }
                }

            currentState.copy(workoutExerciseComponents = updatedWorkoutExerciseComponents)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun addSet(targetedWorkoutExerciseId: String) {

        fun createNewSet(order: Int): SetUI = SetUI(
            id = Uuid.random().toString(),
            workoutExerciseId = targetedWorkoutExerciseId,
            weightGrams = 0f,
            reps = 0,
            distanceMeters = 0f,
            hours = 0,
            minutes = 0,
            seconds = 0,
            order = order
        )

        _state.update { currentState ->
            val updatedWorkoutExerciseComponents =
                currentState.workoutExerciseComponents.map { component ->
                    when (component) {
                        is WorkoutExerciseComponentUI.Single ->
                            if (component.workoutExerciseId == targetedWorkoutExerciseId)
                                component.copy(
                                    workoutExercise = component.workoutExercise.copy(
                                        sets = component.workoutExercise.sets
                                            .let { sets ->
                                                val newSet = createNewSet(order = sets.size + 1)
                                                sets + newSet
                                            }
                                    )
                                )
                            else component

                        is WorkoutExerciseComponentUI.Superset ->
                            component.copy(
                                workoutExercises = component.workoutExercises.map { workoutExercise ->
                                    if (workoutExercise.id == targetedWorkoutExerciseId)
                                        workoutExercise.copy(
                                            sets = workoutExercise.sets
                                                .let { sets ->
                                                    val newSet = createNewSet(order = sets.size + 1)
                                                    sets + newSet
                                                }
                                        )
                                    else workoutExercise
                                }
                            )
                    }
                }
            currentState.copy(workoutExerciseComponents = updatedWorkoutExerciseComponents)
        }
    }

    private fun removeSet(targetedWorkoutExerciseId: String, setId: String) {

        fun List<SetUI>.reassignOrder(): List<SetUI> {
            return mapIndexed { index, set -> set.copy(order = index + 1) }
        }

        _state.update { currentState ->
            val updatedWorkoutExerciseComponents =
                currentState.workoutExerciseComponents.map { component ->
                    when (component) {
                        is WorkoutExerciseComponentUI.Single ->
                            if (component.workoutExerciseId == targetedWorkoutExerciseId)
                                component.copy(
                                    workoutExercise = component.workoutExercise.copy(
                                        sets = component.workoutExercise.sets
                                            .filterNot { it.id == setId }  // Remove target set
                                            .reassignOrder()  // Update order
                                    )
                                )
                            else component

                        is WorkoutExerciseComponentUI.Superset ->
                            component.copy(
                                workoutExercises = component.workoutExercises.map { workoutExercise ->
                                    if (workoutExercise.id == targetedWorkoutExerciseId)
                                        workoutExercise.copy(
                                            sets = workoutExercise.sets
                                                .filterNot { it.id == setId }
                                                .reassignOrder()
                                        )
                                    else workoutExercise
                                }
                            )
                    }
                }
            currentState.copy(workoutExerciseComponents = updatedWorkoutExerciseComponents)
        }
    }

    private fun removeWorkoutExercise(targetWorkoutExerciseId: String) {

        fun List<WorkoutExerciseUI>.reassignOrder(): List<WorkoutExerciseUI> =
            mapIndexed { index, workoutExercise -> workoutExercise.copy(order = index + 1) }

        fun List<WorkoutExerciseComponentUI>.reassignOrder(): List<WorkoutExerciseComponentUI> =
            mapIndexed { index, component -> component.copyComponent(order = index + 1) }

        _state.update { currentState ->
            val updatedWorkoutExerciseComponents = currentState.workoutExerciseComponents
                .mapNotNull { component ->
                    when (component) {
                        is WorkoutExerciseComponentUI.Single ->
                            if (component.workoutExerciseId == targetWorkoutExerciseId) null
                            else component

                        is WorkoutExerciseComponentUI.Superset -> {
                            val filteredExercises = component.workoutExercises
                                .filterNot { it.id == targetWorkoutExerciseId }
                                .reassignOrder()

                            if (filteredExercises.isEmpty()) null
                            else component.copy(workoutExercises = filteredExercises)
                        }
                    }
                }.reassignOrder()

            currentState.copy(workoutExerciseComponents = updatedWorkoutExerciseComponents)
        }
    }

    private fun detachFromSuperset(workoutExerciseId: String) {

        fun List<WorkoutExerciseComponentUI>.reassignOrder(): List<WorkoutExerciseComponentUI> =
            mapIndexed { index, component ->
                component.copyComponent(order = index + 1)
            }

        _state.update { currentState ->
            val updatedComponents = mutableListOf<WorkoutExerciseComponentUI>()

            currentState.workoutExerciseComponents.forEach { component ->
                when (component) {
                    is WorkoutExerciseComponentUI.Single -> {
                        updatedComponents.add(component)
                    }

                    is WorkoutExerciseComponentUI.Superset -> {
                        val remainingExercises =
                            component.workoutExercises.filterNot { it.id == workoutExerciseId }

                        if (remainingExercises.isNotEmpty()) {
                            updatedComponents.add(
                                component.copy(
                                    workoutExercises = remainingExercises,
                                    order = remainingExercises.first().order
                                )
                            )
                        }

                        component.workoutExercises.firstOrNull { it.id == workoutExerciseId }
                            ?.let { detachedExercise ->
                                updatedComponents.add(
                                    WorkoutExerciseComponentUI.Single(
                                        workoutExercise = detachedExercise.copy(supersetId = null),
                                        order = detachedExercise.order,
                                    )
                                )
                            }
                    }
                }
            }

            currentState.copy(workoutExerciseComponents = updatedComponents.reassignOrder())
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun turnIntoSuperset(workoutExerciseId: String) {
        _state.update { currentState ->
            val workoutExerciseComponents = currentState.workoutExerciseComponents
            val updatedWorkoutExerciseComponents = workoutExerciseComponents.map { component ->
                if (component is WorkoutExerciseComponentUI.Single && component.workoutExerciseId == workoutExerciseId) {
                    val workoutExercise = component.workoutExercise
                    val supersetId = Uuid.random().toString()
                    WorkoutExerciseComponentUI.Superset(
                        supersetId = supersetId,
                        workoutExercises = listOf(workoutExercise.copy(supersetId = supersetId)),
                    )
                } else {
                    component
                }
            }
            currentState.copy(
                workoutExerciseComponents = updatedWorkoutExerciseComponents
            )
        }
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
                            workoutExerciseComponents = workout.workoutExercises.toWorkoutExerciseComponentsUI()
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
