package com.itami.workout_flow.auth.presentation.sign_in

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itami.workout_flow.auth.presentation.sign_in.components.AppleSignInButton
import com.itami.workout_flow.auth.presentation.sign_in.components.GoogleSignInButton
import com.itami.workout_flow.core.presentation.components.PulseLoadingAnimation
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.close
import workoutflow.composeapp.generated.resources.icon_close
import workoutflow.composeapp.generated.resources.sign_in
import workoutflow.composeapp.generated.resources.sign_in_text

@Composable
fun SignInScreenRoute(
    onNavigateBack: () -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
    onShowGlobalSnackbar: (message: String) -> Unit,
    viewModel: SignInViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SignInEvent.NavigateBack -> onNavigateBack()
                is SignInEvent.ShowGlobalSnackbar -> onShowGlobalSnackbar(event.message)
                is SignInEvent.ShowLocalSnackbar -> onShowLocalSnackbar(event.message)
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    SignInScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignInScreen(
    state: SignInState,
    onAction: (action: SignInAction) -> Unit,
) {
    Scaffold(
        containerColor = WorkoutFlowTheme.colors.backgroundColors.background,
        contentColor = WorkoutFlowTheme.colors.backgroundColors.onBackground,
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(elevation = 3.dp),
                title = {
                    Text(
                        text = stringResource(Res.string.sign_in),
                        style = WorkoutFlowTheme.typography.titleSmall
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onAction(SignInAction.NavigateBackClick) }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_close),
                            contentDescription = stringResource(Res.string.close),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
                    titleContentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                    navigationIconContentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface
                )
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(horizontal = WorkoutFlowTheme.padding.default)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(Res.string.sign_in_text),
                    style = WorkoutFlowTheme.typography.bodyLarge,
                    color = WorkoutFlowTheme.colors.backgroundColors.onBackgroundVariant,
                    modifier = Modifier.padding(top = WorkoutFlowTheme.padding.extraLarge)
                )
                GoogleButtonUiContainerFirebase(
                    modifier = Modifier.padding(top = WorkoutFlowTheme.padding.extraLarge),
                    scopes = listOf("email", "profile"),
                    linkAccount = false,
                    filterByAuthorizedAccounts = false,
                    onResult = { firebaseUserResult ->
                        onAction(SignInAction.SignInResult(firebaseUserResult))
                    },
                ) {
                    GoogleSignInButton(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isLoading,
                        onClick = this::onClick,
                    )
                }
                AppleSignInButton(
                    modifier = Modifier
                        .padding(top = WorkoutFlowTheme.padding.medium)
                        .fillMaxWidth(),
                    enabled = !state.isLoading,
                    onClick = {
                        // Apple Developer Program membership is required to set up Sign in with Apple.
                        // I don't have one yet, so this feature is not enabled.
                    },
                )
            }
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                visible = state.isLoading
            ) {
                PulseLoadingAnimation(modifier = Modifier.size(48.dp))
            }
        }
    }
}