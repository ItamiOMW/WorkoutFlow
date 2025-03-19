package com.itami.workout_flow.workouts.presentation.screens.workout_editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itami.workout_flow.core.presentation.components.DialogComponent
import com.itami.workout_flow.core.presentation.components.GradientFloatingActionButton
import com.itami.workout_flow.core.presentation.components.collapsing_top_bar.rememberCollapsingTopAppBarScrollBehavior
import com.itami.workout_flow.core.presentation.navigation.utils.NavResultCallback
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.components.EditorWorkoutExerciseItem
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.components.WorkoutEditorDurationSheetContent
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.components.WorkoutEditorEquipmentSheetContent
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.components.WorkoutEditorMusclesSheetContent
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.components.WorkoutEditorTopBar
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.components.WorkoutEditorWorkoutTypeSheetContent
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.add_exercise
import workoutflow.composeapp.generated.resources.are_you_sure_you_want_to_delete_workout
import workoutflow.composeapp.generated.resources.cancel
import workoutflow.composeapp.generated.resources.delete
import workoutflow.composeapp.generated.resources.delete_workout
import workoutflow.composeapp.generated.resources.exit_and_discard
import workoutflow.composeapp.generated.resources.icon_add
import workoutflow.composeapp.generated.resources.unsaved_changes
import workoutflow.composeapp.generated.resources.unsaved_changes_desc

@Composable
fun WorkoutEditorScreenRoute(
    onNavigateToSearchExercise: (NavResultCallback<Exercise?>) -> Unit,
    onNavigateBack: () -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
    viewModel: WorkoutEditorViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is WorkoutEditorEvent.NavigateBack -> onNavigateBack()
                is WorkoutEditorEvent.ShowSnackbar -> onShowLocalSnackbar(event.message)

                is WorkoutEditorEvent.NavigateToSearchExercise -> {
                    onNavigateToSearchExercise { exercise ->
                        exercise?.let {
                            viewModel.onAction(WorkoutEditorAction.WorkoutExerciseNavResult(it))
                        }
                    }
                }

                is WorkoutEditorEvent.NavigateToSearchExerciseForSuperset -> {
                    onNavigateToSearchExercise { exercise ->
                        exercise?.let {
                            viewModel.onAction(
                                WorkoutEditorAction.SupersetWorkoutExerciseNavResult(
                                    supersetId = event.supersetId,
                                    exercise = it
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    WorkoutEditorScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkoutEditorScreen(
    state: WorkoutEditorState,
    onAction: (WorkoutEditorAction) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val dismissBottomSheet = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onAction(WorkoutEditorAction.DismissBottomSheet)
            }
        }
    }

    if (state.bottomSheetContent != null) {
        ModalBottomSheet(
            containerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
            contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            sheetState = sheetState,
            onDismissRequest = { onAction(WorkoutEditorAction.DismissBottomSheet) }
        ) {
            when (state.bottomSheetContent) {
                WorkoutEditorState.BottomSheetContent.DURATION -> {
                    WorkoutEditorDurationSheetContent(
                        modifier = Modifier.padding(WorkoutFlowTheme.padding.default),
                        initialDurationMin = state.durationMin ?: 25,
                        onApply = { durationMin ->
                            onAction(WorkoutEditorAction.ChangeDuration(durationMin))
                            dismissBottomSheet()
                        }
                    )
                }

                WorkoutEditorState.BottomSheetContent.EQUIPMENT -> {
                    WorkoutEditorEquipmentSheetContent(
                        modifier = Modifier.padding(WorkoutFlowTheme.padding.default),
                        initialEquipment = state.equipment,
                        onApply = { equipment ->
                            onAction(WorkoutEditorAction.ChangeEquipment(equipment))
                            dismissBottomSheet()
                        }
                    )
                }

                WorkoutEditorState.BottomSheetContent.WORKOUT_TYPE -> {
                    WorkoutEditorWorkoutTypeSheetContent(
                        modifier = Modifier.padding(WorkoutFlowTheme.padding.default),
                        initialWorkoutTypes = state.workoutTypes,
                        onApply = { workoutTypes ->
                            onAction(WorkoutEditorAction.ChangeWorkoutTypes(workoutTypes))
                            dismissBottomSheet()
                        }
                    )
                }

                WorkoutEditorState.BottomSheetContent.MUSCLES -> {
                    WorkoutEditorMusclesSheetContent(
                        modifier = Modifier.padding(WorkoutFlowTheme.padding.default),
                        initialMuscles = state.muscles,
                        onApply = { muscles ->
                            onAction(WorkoutEditorAction.ChangeMuscles(muscles))
                            dismissBottomSheet()
                        }
                    )
                }
            }
        }
    }

    if (state.showExitDialog) {
        DialogComponent(
            title = stringResource(Res.string.unsaved_changes),
            description = stringResource(Res.string.unsaved_changes_desc),
            cancelText = stringResource(Res.string.cancel),
            confirmText = stringResource(Res.string.exit_and_discard),
            onConfirm = {
                onAction(WorkoutEditorAction.ConfirmNavigateBack)
            },
            onDismiss = {
                onAction(WorkoutEditorAction.DismissNavigateBackDialog)
            }
        )
    }

    if (state.showDeleteWorkoutDialog) {
        DialogComponent(
            title = stringResource(Res.string.delete_workout),
            description = stringResource(Res.string.are_you_sure_you_want_to_delete_workout),
            cancelText = stringResource(Res.string.cancel),
            confirmText = stringResource(Res.string.delete),
            onConfirm = {
                onAction(WorkoutEditorAction.ConfirmDeleteWorkout)
            },
            onDismiss = {
                onAction(WorkoutEditorAction.DismissDeleteWorkoutDialog)
            }
        )
    }

    val topBarScrollBehavior = rememberCollapsingTopAppBarScrollBehavior()

    val workoutExercisesListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        containerColor = WorkoutFlowTheme.colors.backgroundColors.background,
        contentColor = WorkoutFlowTheme.colors.backgroundColors.onBackground,
        topBar = {
            WorkoutEditorTopBar(
                topBarScrollBehavior = topBarScrollBehavior,
                workoutName = state.workoutName,
                workoutDesc = state.workoutDesc,
                equipments = state.equipment,
                workoutTypes = state.workoutTypes,
                muscles = state.muscles,
                durationMin = state.durationMin,
                isVisibleToOthers = state.isVisibleToOthers,
                isEditMode = state.isEditMode,
                onWorkoutNameChange = { newValue ->
                    onAction(WorkoutEditorAction.ChangeWorkoutName(newValue))
                },
                onWorkoutDescChange = { newValue ->
                    onAction(WorkoutEditorAction.ChangeWorkoutDesc(newValue))
                },
                onEditDurationClick = {
                    onAction(WorkoutEditorAction.OpenEditDurationSheet)
                },
                onEditEquipmentClick = {
                    onAction(WorkoutEditorAction.OpenEditEquipmentSheet)
                },
                onEditWorkoutTypesClick = {
                    onAction(WorkoutEditorAction.OpenEditWorkoutTypesSheet)
                },
                onEditMusclesClick = {
                    onAction(WorkoutEditorAction.OpenEditMusclesSheet)
                },
                onVisibleToOthersChange = { visible ->
                    onAction(WorkoutEditorAction.ChangeVisibleToOthers(visible))
                },
                onSaveWorkoutClick = {
                    onAction(WorkoutEditorAction.SaveWorkout)
                },
                onDeleteWorkoutClick = {
                    onAction(WorkoutEditorAction.DeleteWorkout)
                },
                onNavigateBackClick = {
                    onAction(WorkoutEditorAction.NavigateBack)
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = !workoutExercisesListState.isScrollInProgress,
                enter = fadeIn(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(100)),
            ) {
                GradientFloatingActionButton(
                    containerBrush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
                    contentColor = WorkoutFlowTheme.colors.brandColors.onPrimary,
                    shape = WorkoutFlowTheme.shapes.large,
                    onClick = { onAction(WorkoutEditorAction.AddExercise) },
                    minHeight = 56.dp,
                    minWidth = 56.dp
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_add),
                        contentDescription = stringResource(Res.string.add_exercise),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        LazyColumn(
            state = workoutExercisesListState,
            contentPadding = PaddingValues(vertical = WorkoutFlowTheme.padding.medium),
            verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.medium),
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = WorkoutFlowTheme.padding.default),
        ) {
            items(
                items = state.workoutExerciseComponents,
                key = { it.workoutExerciseId }) { workoutExercise ->
                EditorWorkoutExerciseItem(
                    modifier = Modifier.fillMaxWidth(),
                    workoutExerciseComponent = workoutExercise,
                    weightUnit = state.weightUnit,
                    distanceUnit = state.distanceUnit,
                    onClick = { },
                    onSetValuesChange = { workoutsExerciseId, set ->
                        onAction(
                            WorkoutEditorAction.ChangeSetValues(
                                workoutExerciseId = workoutsExerciseId,
                                updatedSetUI = set,
                            )
                        )
                    },
                    onAddSet = { workoutExerciseId ->
                        onAction(WorkoutEditorAction.AddSet(workoutExerciseId))
                    },
                    onAddSupersetExercise = { supersetId ->
                        onAction(
                            WorkoutEditorAction.AddSupersetExercise(
                                supersetId = supersetId
                            )
                        )
                    },
                    onTurnIntoSuperset = { workoutExerciseId ->
                        onAction(WorkoutEditorAction.TurnIntoSuperset(workoutExerciseId))
                    },
                    onDetachFromSuperset = { workoutExerciseId ->
                        onAction(WorkoutEditorAction.DetachFromSuperset(workoutExerciseId))
                    },
                    onRemoveSet = { workoutExerciseId, setId ->
                        onAction(WorkoutEditorAction.RemoveSet(workoutExerciseId, setId))
                    },
                    onRemoveWorkoutExercise = { workoutExerciseId ->
                        onAction(WorkoutEditorAction.RemoveWorkoutExercise(workoutExerciseId))
                    },
                    onExpandedStateChange = { expanded ->
                        onAction(
                            WorkoutEditorAction.ChangeWorkoutExerciseExpandedState(
                                workoutExerciseId = workoutExercise.workoutExerciseId,
                                expanded = expanded
                            )
                        )
                    },
                )
            }

            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}