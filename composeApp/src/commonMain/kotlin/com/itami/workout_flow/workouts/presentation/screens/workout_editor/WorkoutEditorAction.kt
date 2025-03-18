package com.itami.workout_flow.workouts.presentation.screens.workout_editor

import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutType
import com.itami.workout_flow.workouts.presentation.model.SetUI

sealed interface WorkoutEditorAction {

    data class ChangeWorkoutName(val newValue: String) : WorkoutEditorAction
    data class ChangeWorkoutDesc(val newValue: String) : WorkoutEditorAction
    data class ChangeVisibleToOthers(val isVisible: Boolean) : WorkoutEditorAction
    data class ChangeDuration(val durationMin: Int) : WorkoutEditorAction
    data class ChangeEquipment(val equipments: List<Equipment>) : WorkoutEditorAction
    data class ChangeWorkoutTypes(val types: List<WorkoutType>) : WorkoutEditorAction
    data class ChangeMuscles(val muscles: List<Muscle>) : WorkoutEditorAction

    data class ChangeWorkoutExerciseExpandedState(
        val workoutExerciseId: String,
        val expanded: Boolean,
    ) : WorkoutEditorAction

    data class AddSet(
        val workoutExerciseId: String,
    ) : WorkoutEditorAction

    data class ChangeSetValues(
        val workoutExerciseId: String,
        val updatedSetUI: SetUI,
    ) : WorkoutEditorAction

    data class RemoveSet(
        val workoutExerciseId: String,
        val setId: String,
    ) : WorkoutEditorAction

    data class RemoveWorkoutExercise(
        val workoutExerciseId: String,
    ) : WorkoutEditorAction

    data class AddSupersetExercise(
        val supersetId: String,
    ) : WorkoutEditorAction

    data class SupersetWorkoutExerciseNavResult(
        val supersetId: String,
        val exercise: Exercise,
    ) : WorkoutEditorAction

    data object AddExercise : WorkoutEditorAction

    data class WorkoutExerciseNavResult(
        val exercise: Exercise
    ) : WorkoutEditorAction

    data object SaveWorkout : WorkoutEditorAction
    data object OpenEditDurationSheet : WorkoutEditorAction
    data object OpenEditEquipmentSheet : WorkoutEditorAction
    data object OpenEditWorkoutTypesSheet : WorkoutEditorAction
    data object OpenEditMusclesSheet : WorkoutEditorAction
    data object DismissBottomSheet : WorkoutEditorAction
    data object DeleteWorkout : WorkoutEditorAction
    data object ConfirmDeleteWorkout : WorkoutEditorAction
    data object DismissDeleteWorkoutDialog : WorkoutEditorAction
    data object NavigateBack : WorkoutEditorAction
    data object ConfirmNavigateBack : WorkoutEditorAction
    data object DismissNavigateBackDialog : WorkoutEditorAction

}
