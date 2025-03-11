package com.itami.workout_flow.workouts.presentation.screens.workouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.PagingData
import com.itami.workout_flow.core.domain.model.workout.WorkoutPreview
import com.itami.workout_flow.core.presentation.components.GradientFloatingActionButton
import com.itami.workout_flow.core.presentation.navigation.AppGraph.Workouts.WorkoutsScreen.WorkoutsLaunchMode
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.workouts.presentation.screens.workouts.components.WorkoutsCustomPagerScreenContent
import com.itami.workout_flow.workouts.presentation.screens.workouts.components.WorkoutsFavoritesPagerScreenContent
import com.itami.workout_flow.workouts.presentation.screens.workouts.components.WorkoutsScreenFilterBottomSheetContent
import com.itami.workout_flow.workouts.presentation.screens.workouts.components.WorkoutsScreenSortBottomSheetContent
import com.itami.workout_flow.workouts.presentation.screens.workouts.components.WorkoutsSearchPagerScreenContent
import com.itami.workout_flow.workouts.presentation.screens.workouts.components.WorkoutsTopBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.create_workout
import workoutflow.composeapp.generated.resources.icon_edit

@Composable
fun WorkoutsScreenRoute(
    onNavigateToWorkoutDetails: (workoutId: String) -> Unit,
    onNavigateToWorkoutEditor: () -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
    workoutsLaunchMode: WorkoutsLaunchMode = WorkoutsLaunchMode.Default,
    viewModel: WorkoutsViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is WorkoutsEvent.NavigateToWorkoutDetails -> onNavigateToWorkoutDetails(event.workoutId)
                is WorkoutsEvent.ShowLocalSnackbar -> onShowLocalSnackbar(event.message)
                is WorkoutsEvent.NavigateToCreateWorkout -> onNavigateToWorkoutEditor()
            }
        }
    }

    val searchWorkoutsFlow = viewModel.searchWorkouts
    val customWorkoutsFlow = viewModel.customWorkouts
    val favoriteWorkoutsFlow = viewModel.favoriteWorkouts
    val state by viewModel.state.collectAsStateWithLifecycle()
    WorkoutsScreenContent(
        workoutsLaunchMode = workoutsLaunchMode,
        state = state,
        customWorkoutsFlow = customWorkoutsFlow,
        favoriteWorkoutsFlow = favoriteWorkoutsFlow,
        searchWorkoutsPagingItems = searchWorkoutsFlow,
        onAction = viewModel::onAction,
        onShowSnackbar = onShowLocalSnackbar,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkoutsScreenContent(
    workoutsLaunchMode: WorkoutsLaunchMode,
    state: WorkoutsState,
    customWorkoutsFlow: Flow<List<WorkoutPreview>>,
    favoriteWorkoutsFlow: Flow<List<WorkoutPreview>>,
    searchWorkoutsPagingItems: Flow<PagingData<WorkoutPreview>>,
    onAction: (WorkoutsAction) -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val pagerScreens = WorkoutsState.WorkoutsPagerScreen.entries

    val pagerState = rememberPagerState(
        initialPage = when (workoutsLaunchMode) {
            WorkoutsLaunchMode.Default -> 0
            WorkoutsLaunchMode.Favorites -> 1
            WorkoutsLaunchMode.Search -> 2
        }
    ) { pagerScreens.size }

    val customWorkoutsLazyListState = rememberLazyListState()
    val favoriteWorkoutsLazyListState = rememberLazyListState()
    val searchWorkoutsLazyListState = rememberLazyListState()

    if (state.workoutsBottomSheetContent != null) {
        ModalBottomSheet(
            containerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
            contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            sheetState = sheetState,
            onDismissRequest = { onAction(WorkoutsAction.HideBottomSheet) }
        ) {
            when (state.workoutsBottomSheetContent) {
                WorkoutsState.WorkoutsBottomSheetContent.SORT -> {
                    WorkoutsScreenSortBottomSheetContent(
                        modifier = Modifier.fillMaxWidth(),
                        selectedWorkoutSort = state.workoutSort,
                        onApplyClick = { workoutSort ->
                            onAction(WorkoutsAction.ChangeWorkoutsSort(workoutSort))
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onAction(WorkoutsAction.HideBottomSheet)
                                }
                            }
                        }
                    )
                }

                WorkoutsState.WorkoutsBottomSheetContent.FILTER -> {
                    WorkoutsScreenFilterBottomSheetContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f),
                        workoutsFilter = state.workoutsFilter,
                        onApplyClick = { workoutsFilter ->
                            onAction(WorkoutsAction.ChangeWorkoutsFilter(workoutsFilter))
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onAction(WorkoutsAction.HideBottomSheet)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        containerColor = WorkoutFlowTheme.colors.backgroundColors.background,
        contentColor = WorkoutFlowTheme.colors.backgroundColors.onBackground,
        topBar = {
            WorkoutsTopBar(
                currentPageIndex = pagerState.currentPage,
                pagerScreens = pagerScreens,
                searchQuery = state.searchQuery,
                showSearchQuery = state.showSearchQuery,
                scrollBehavior = topAppBarScrollBehavior,
                onSearchQueryChange = { newValue ->
                    onAction(WorkoutsAction.SearchQueryChange(newValue))
                },
                onCloseSearchClick = {
                    onAction(WorkoutsAction.CloseSearchClick)
                },
                onOpenSearchClick = {
                    onAction(WorkoutsAction.OpenSearchClick)
                },
                onSortClick = {
                    onAction(WorkoutsAction.SortClick)
                },
                onFilterClick = {
                    onAction(WorkoutsAction.FilterClick)
                },
                onPagerTabClick = { pageIndex ->
                    scope.launch {
                        pagerState.animateScrollToPage(pageIndex)
                    }
                },
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                modifier = Modifier.padding(bottom = 72.dp),
                visible = pagerScreens[pagerState.currentPage] == WorkoutsState.WorkoutsPagerScreen.CUSTOM &&
                        !customWorkoutsLazyListState.isScrollInProgress,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                GradientFloatingActionButton(
                    containerBrush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
                    contentColor = WorkoutFlowTheme.colors.brandColors.onPrimary,
                    shape = WorkoutFlowTheme.shapes.large,
                    onClick = { onAction(WorkoutsAction.CreateWorkoutFABClick) },
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_edit),
                        contentDescription = stringResource(Res.string.create_workout),
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        HorizontalPager(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize(),
            state = pagerState,
        ) { pageIndex ->
            when (pagerScreens[pageIndex]) {
                WorkoutsState.WorkoutsPagerScreen.CUSTOM -> {
                    WorkoutsCustomPagerScreenContent(
                        modifier = Modifier.fillMaxSize(),
                        lazyListState = customWorkoutsLazyListState,
                        customWorkoutsPreviewFlow = customWorkoutsFlow,
                        onWorkoutPreviewClick = { workout ->
                            onAction(WorkoutsAction.WorkoutClick(workout.id))
                        }
                    )
                }

                WorkoutsState.WorkoutsPagerScreen.FAVORITES -> {
                    WorkoutsFavoritesPagerScreenContent(
                        modifier = Modifier.fillMaxSize(),
                        lazyListState = favoriteWorkoutsLazyListState,
                        favoriteWorkoutsPreviewsFlow = favoriteWorkoutsFlow,
                        onWorkoutPreviewClick = { workout ->
                            onAction(WorkoutsAction.WorkoutClick(workout.id))
                        }
                    )
                }

                WorkoutsState.WorkoutsPagerScreen.SEARCH -> {
                    WorkoutsSearchPagerScreenContent(
                        modifier = Modifier.fillMaxSize(),
                        searchWorkoutsPreviewsPagingItemsFlow = searchWorkoutsPagingItems,
                        lazyListState = searchWorkoutsLazyListState,
                        onWorkoutPreviewClick = { workout ->
                            onAction(WorkoutsAction.WorkoutClick(workout.id))
                        },
                        onErrorOccurred = { message ->
                            scope.launch {
                                onShowSnackbar(message)
                            }
                        }
                    )
                }
            }
        }
    }
}