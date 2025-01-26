package com.itami.workout_flow.home.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoute(
    onNavigateBack: () -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeEvent.ShowSnackbar -> onShowLocalSnackbar(event.message)
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (action: HomeAction) -> Unit,
) {

}