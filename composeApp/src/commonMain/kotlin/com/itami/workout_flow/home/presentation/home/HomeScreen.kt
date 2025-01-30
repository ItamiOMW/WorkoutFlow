package com.itami.workout_flow.home.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itami.workout_flow.core.presentation.components.SignInCard
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.home.presentation.home.components.HomeBlurredCircle
import com.itami.workout_flow.home.presentation.home.components.HomeRoutineSection
import com.itami.workout_flow.home.presentation.home.components.HomeSearchBar
import com.itami.workout_flow.home.presentation.home.components.HomeUserGreetingSection
import com.itami.workout_flow.home.presentation.home.components.HomeWorkoutTypesSection
import com.itami.workout_flow.model.WorkoutType
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoute(
    onNavigateToProfile: () -> Unit,
    onNavigateToSearchWorkout: () -> Unit,
    onNavigateToSearchWorkoutByType: (WorkoutType) -> Unit,
    onNavigateToRoutine: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToWorkoutDetails: (workoutId: String) -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeEvent.ShowSnackbar -> onShowLocalSnackbar(event.message)
                HomeEvent.NavigateToProfile -> onNavigateToProfile()
                HomeEvent.NavigateToRoutine -> onNavigateToRoutine()
                HomeEvent.NavigateToSearchWorkout -> onNavigateToSearchWorkout()
                HomeEvent.NavigateToSignIn -> onNavigateToSignIn()
                is HomeEvent.NavigateToWorkoutDetails -> onNavigateToWorkoutDetails(event.workoutId)
                is HomeEvent.NavigateToSearchWorkoutByType -> onNavigateToSearchWorkoutByType(event.workoutType)
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
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        contentAlignment = Alignment.TopCenter
    ) {
        HomeBlurredCircle()
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeUserGreetingSection(
                modifier = Modifier
                    .padding(
                        top = WorkoutFlowTheme.padding.medium,
                        start = WorkoutFlowTheme.padding.default,
                        end = WorkoutFlowTheme.padding.default
                    )
                    .fillMaxWidth(),
                currentUser = state.currentUser,
                onProfilePictureClick = { onAction(HomeAction.ProfilePictureClick) }
            )
            HomeSearchBar(
                modifier = Modifier
                    .padding(
                        top = WorkoutFlowTheme.padding.large,
                        start = WorkoutFlowTheme.padding.default,
                        end = WorkoutFlowTheme.padding.default,
                    )
                    .fillMaxWidth(),
                onClick = { onAction(HomeAction.SearchBarClick) }
            )
            HomeRoutineSection(
                modifier = Modifier
                    .padding(top = WorkoutFlowTheme.padding.large)
                    .fillMaxWidth(),
                currentDate = state.currentDate,
                selectedRoutineDay = state.selectedRoutineDay,
                routineDays = state.routineDays,
                onRoutineDayClick = { routineDay ->
                    onAction(HomeAction.RoutineDayClick(routineDay))
                },
                onEditRoutineClick = {
                    onAction(HomeAction.EditRoutineClick)
                },
                onScheduledWorkoutClick = { scheduledWorkout ->
                    onAction(HomeAction.ScheduledWorkoutClick(scheduledWorkout))
                },
            )
            HomeWorkoutTypesSection(
                modifier = Modifier
                    .padding(top = WorkoutFlowTheme.padding.large)
                    .fillMaxWidth(),
                types = WorkoutType.entries,
                onWorkoutTypeClick = { type ->
                    onAction(HomeAction.WorkoutTypeClick(type))
                },
            )
            AnimatedVisibility(visible = state.showSignInCard) {
                SignInCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = WorkoutFlowTheme.padding.extraLarge,
                            start = WorkoutFlowTheme.padding.default,
                            end = WorkoutFlowTheme.padding.default,
                        ),
                    onClick = {
                        onAction(HomeAction.SignInCardClick)
                    },
                    onHide = {
                        onAction(HomeAction.HideSignInCardClick)
                    }
                )
            }
            Spacer(modifier = Modifier.height(124.dp))
        }
    }
}