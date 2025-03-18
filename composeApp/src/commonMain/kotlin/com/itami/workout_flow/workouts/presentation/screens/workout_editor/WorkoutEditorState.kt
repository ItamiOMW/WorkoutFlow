package com.itami.workout_flow.workouts.presentation.screens.workout_editor

import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutType
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseComponentUI

data class WorkoutEditorState(
    val workoutName: String = "",
    val workoutDesc: String = "",
    val workoutTypes: List<WorkoutType> = emptyList(),
    val muscles: List<Muscle> = emptyList(),
    val equipment: List<Equipment> = emptyList(),
    val durationMin: Int? = null,
    val isVisibleToOthers: Boolean = false,
    val workoutExercises: List<WorkoutExerciseComponentUI> = emptyList(),
    val isEditMode: Boolean = false,
    val isSavingWorkout: Boolean = false,
    val showExitDialog: Boolean = false,
    val showDeleteWorkoutDialog: Boolean = false,
    val bottomSheetContent: BottomSheetContent? = null,
    val weightUnit: WeightUnit = WeightUnit.KILOGRAMS,
    val distanceUnit: DistanceUnit = DistanceUnit.KILOMETERS,
) {
    enum class BottomSheetContent {
        DURATION,
        EQUIPMENT,
        WORKOUT_TYPE,
        MUSCLES;
    }
}
