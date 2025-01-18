package com.itami.workout_flow.onboarding.presentation.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingScreenRoot(
    onNavigateToHome: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                OnboardingEvent.NavigateToHomeScreen -> onNavigateToHome()
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    OnboardingScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun OnboardingScreen(
    state: OnboardingState,
    onAction: (action: OnboardingAction) -> Unit,
) {

}