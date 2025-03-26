package com.itami.workout_flow.workouts.presentation.view_model

import androidx.lifecycle.ViewModel
import com.itami.workout_flow.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedExerciseViewModel : ViewModel() {

    private val _selectedExercise = MutableStateFlow<Exercise?>(null)
    val selectedExercise = _selectedExercise.asStateFlow()

    fun onSelectExercise(exercise: Exercise?) {
        _selectedExercise.update { exercise }
    }

}