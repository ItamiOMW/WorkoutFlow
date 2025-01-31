package com.itami.workout_flow.auth.presentation.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itami.workout_flow.BuildKonfig
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreenRoute(
    onNavigateBack: () -> Unit,
    viewModel: SignInViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SignInEvent.NavigateBack -> onNavigateBack()
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    SignInScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun SignInScreen(
    state: SignInState,
    onAction: (action: SignInAction) -> Unit,
) {
    val googleWebClientId = BuildKonfig.GOOGLE_WEB_CLIENT_ID
}