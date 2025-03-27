package com.itami.workout_flow.workouts.presentation.screens.search_exercise

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.PagingData
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.workouts.presentation.screens.search_exercise.components.SearchExerciseExerciseList
import com.itami.workout_flow.workouts.presentation.screens.search_exercise.components.SearchExerciseFilterSheetContent
import com.itami.workout_flow.workouts.presentation.screens.search_exercise.components.SearchExerciseTopBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchExerciseScreenRoute(
    onNavigateToExerciseDetails: (exerciseId: Long) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateBackWithResult: (exercise: Exercise) -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
    viewModel: SearchExerciseViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is SearchExerciseEvent.NavigateToExerciseDetails -> onNavigateToExerciseDetails(event.exerciseId)
                is SearchExerciseEvent.NavigateBackWithResult -> onNavigateBackWithResult(event.exercise)
                is SearchExerciseEvent.NavigateBack -> onNavigateBack()
                is SearchExerciseEvent.ShowLocalSnackbar -> onShowLocalSnackbar(event.message)
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchExerciseScreen(
        state = state,
        exercisesPagingData = viewModel.exercisesPagingData,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchExerciseScreen(
    state: SearchExerciseState,
    exercisesPagingData: Flow<PagingData<Exercise>>,
    onAction: (action: SearchExerciseAction) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val lazyListState = rememberLazyListState()

    if (state.showExerciseFilterSheet) {
        ModalBottomSheet(
            containerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
            contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            sheetState = sheetState,
            onDismissRequest = { onAction(SearchExerciseAction.HideFilterSheet) }
        ) {
            SearchExerciseFilterSheetContent(
                modifier = Modifier.fillMaxWidth(),
                exercisesFilter = state.exercisesFilter,
                onApplyClick = { exerciseFilter ->
                    onAction(SearchExerciseAction.ExerciseFilterChange(exerciseFilter))
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onAction(SearchExerciseAction.HideFilterSheet)
                        }
                    }
                }
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WorkoutFlowTheme.colors.backgroundColors.background,
        contentColor = WorkoutFlowTheme.colors.backgroundColors.onBackground,
        topBar = {
            SearchExerciseTopBar(
                modifier = Modifier.fillMaxWidth(),
                searchExerciseLaunchMode = state.launchMode,
                searchQuery = state.searchQuery,
                showSearchQuery = state.showSearchQuery,
                scrollBehavior = topAppBarScrollBehavior,
                onSearchQueryChange = { newValue ->
                    onAction(SearchExerciseAction.SearchQueryChange(newValue))
                },
                onNavigateBackClick = {
                    onAction(SearchExerciseAction.NavigateBack)
                },
                onOpenSearchClick = {
                    onAction(SearchExerciseAction.ShowSearchQuery)
                },
                onCloseSearchClick = {
                    onAction(SearchExerciseAction.HideSearchQuery)
                },
                onFilterClick = {
                    onAction(SearchExerciseAction.ShowFilterSheet)
                }
            )
        }
    ) { scaffoldPadding ->
        SearchExerciseExerciseList(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize(),
            exercisesPagingData = exercisesPagingData,
            lazyListState = lazyListState,
            onExerciseClick = { exercise ->
                onAction(SearchExerciseAction.ExerciseClick(exercise))
            },
            onExerciseGifClick = { exercise ->
                onAction(SearchExerciseAction.ExerciseGifClick(exercise))
            },
            onErrorOccurred = { errorMessage ->
                onAction(SearchExerciseAction.ErrorOccurred(errorMessage))
            }
        )
    }
}