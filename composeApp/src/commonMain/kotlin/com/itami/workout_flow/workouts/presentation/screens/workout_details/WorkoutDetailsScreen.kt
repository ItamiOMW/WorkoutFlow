package com.itami.workout_flow.workouts.presentation.screens.workout_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itami.workout_flow.core.presentation.components.GradientFloatingActionButton
import com.itami.workout_flow.core.presentation.components.collapsing_top_bar.rememberCollapsingTopAppBarScrollBehavior
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.workouts.presentation.screens.workout_details.components.WorkoutDetailsExerciseList
import com.itami.workout_flow.workouts.presentation.screens.workout_details.components.WorkoutDetailsTopBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.icon_play_arrow
import workoutflow.composeapp.generated.resources.start_workout
import workoutflow.composeapp.generated.resources.workout_not_found

@Composable
fun WorkoutDetailsScreenRoute(
    onNavigateToEditWorkout: (workoutId: String) -> Unit,
    onNavigateToUserProfile: (userId: Long) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: WorkoutDetailsViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                WorkoutDetailsEvent.NavigateBack -> onNavigateBack()
                is WorkoutDetailsEvent.NavigateToEditWorkout -> onNavigateToEditWorkout(event.workoutId)
                is WorkoutDetailsEvent.NavigateToUserProfile -> onNavigateToUserProfile(event.userId)
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    WorkoutDetailsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun WorkoutDetailsScreen(
    state: WorkoutDetailsState,
    onAction: (action: WorkoutDetailsAction) -> Unit,
) {
    val topBarScrollBehavior = rememberCollapsingTopAppBarScrollBehavior()

    val exercisesLazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        containerColor = WorkoutFlowTheme.colors.backgroundColors.background,
        contentColor = WorkoutFlowTheme.colors.backgroundColors.onBackground,
        topBar = {
            WorkoutDetailsTopBar(
                topBarScrollBehavior = topBarScrollBehavior,
                state = state,
                onNavigateBackClick = {
                    onAction(WorkoutDetailsAction.NavigateBackClick)
                },
                onFavoriteClick = {
                    onAction(WorkoutDetailsAction.FavoriteClick)
                },
                onAuthorProfileImageClick = {
                    onAction(WorkoutDetailsAction.AuthorProfileImageClick)
                },
                onStartWorkoutDropdownItemClick = {
                    onAction(WorkoutDetailsAction.StartWorkoutDropdownItemClick)
                },
                onAuthorProfileDropdownItemClick = {
                    onAction(WorkoutDetailsAction.AuthorProfileDropdownItemClick)
                },
                onEditWorkoutDropdownItemClick = {
                    onAction(WorkoutDetailsAction.EditWorkoutDropdownItemClick)
                },
                onMoreOptionsClick = {
                    onAction(WorkoutDetailsAction.MoreOptionsClick)
                },
                onDismissDropdownRequest = {
                    onAction(WorkoutDetailsAction.DismissDropdownRequest)
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = !exercisesLazyListState.isScrollInProgress,
                enter = fadeIn(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(100)),
            ) {
                GradientFloatingActionButton(
                    containerBrush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
                    contentColor = WorkoutFlowTheme.colors.brandColors.onPrimary,
                    shape = WorkoutFlowTheme.shapes.large,
                    onClick = { onAction(WorkoutDetailsAction.StartWorkoutFABClick) },
                    minHeight = 56.dp,
                    minWidth = 56.dp
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_play_arrow),
                        contentDescription = stringResource(Res.string.start_workout),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        when (state) {
            is WorkoutDetailsState.Workout -> {
                WorkoutDetailsExerciseList(
                    modifier = Modifier.padding(scaffoldPadding),
                    workout = state.workoutUI,
                    weightUnit = state.weightUnit,
                    distanceUnit = state.distanceUnit,
                    lazyListState = exercisesLazyListState,
                    onExpandedStateChange = { workoutExerciseUUID, expanded ->
                        onAction(
                            WorkoutDetailsAction.WorkoutExerciseExpandedStateChange(
                                workoutExerciseId = workoutExerciseUUID,
                                expanded = expanded
                            )
                        )
                    },
                    onExerciseGifClick = {
                        // TODO Navigate to exercise details
                    }
                )
            }

            WorkoutDetailsState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(Res.string.workout_not_found),
                        style = WorkoutFlowTheme.typography.bodyLarge,
                        color = WorkoutFlowTheme.colors.backgroundColors.onBackgroundVariant
                    )
                }
            }

            WorkoutDetailsState.Initializing -> {
                Unit
            }
        }
    }
}