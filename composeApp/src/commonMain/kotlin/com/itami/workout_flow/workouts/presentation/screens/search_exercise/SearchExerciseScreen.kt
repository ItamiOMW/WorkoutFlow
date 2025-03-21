package com.itami.workout_flow.workouts.presentation.screens.search_exercise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchExerciseScreenRoute(
    onNavigateBack: () -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
    viewModel: SearchExerciseViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SearchExerciseEvent.NavigateBack -> onNavigateBack()
                is SearchExerciseEvent.ShowLocalSnackbar -> onShowLocalSnackbar(event.message)
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchExerciseScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun SearchExerciseScreen(
    state: SearchExerciseState,
    onAction: (action: SearchExerciseAction) -> Unit,
) {

}