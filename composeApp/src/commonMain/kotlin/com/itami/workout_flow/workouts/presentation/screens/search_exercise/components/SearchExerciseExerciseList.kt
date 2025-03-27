package com.itami.workout_flow.workouts.presentation.screens.search_exercise.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.collectAsLazyPagingItems
import com.itami.workout_flow.core.presentation.utils.itemKey
import com.itami.workout_flow.core.presentation.utils.toStringRes
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.workouts.presentation.components.ExerciseItem
import com.itami.workout_flow.workouts.presentation.components.ShimmerExerciseItem
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.error_unknown

@Composable
internal fun SearchExerciseExerciseList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    exercisesPagingData: Flow<PagingData<Exercise>>,
    onExerciseClick: (Exercise) -> Unit,
    onExerciseGifClick: (Exercise) -> Unit,
    onErrorOccurred: (m: String) -> Unit,
) {
    val exercisesPagingItems = exercisesPagingData.collectAsLazyPagingItems()

    // State to track the last displayed error and its handling status
    var lastDisplayedError by remember { mutableStateOf<String?>(null) }
    var isErrorDisplayed by remember { mutableStateOf(false) }

    LaunchedEffect(exercisesPagingItems.loadState) {
        val errorMessage = getCurrentErrorMessage(exercisesPagingItems.loadState)

        // Handle error message if it's new or if the previous error has been cleared
        if (errorMessage != null && (errorMessage != lastDisplayedError || !isErrorDisplayed)) {
            lastDisplayedError = errorMessage
            isErrorDisplayed = true
            onErrorOccurred(errorMessage)
        } else if (errorMessage == null) {
            // Reset error handing status when no error is present
            isErrorDisplayed = false
        }
    }

    LazyColumn(
        modifier = modifier.animateContentSize(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.medium),
        contentPadding = PaddingValues(WorkoutFlowTheme.padding.default)
    ) {
        if (exercisesPagingItems.loadState.refresh !is LoadState.Loading) {
            items(
                count = exercisesPagingItems.itemCount,
                key = exercisesPagingItems.itemKey { it.id },
            ) { index ->
                val exercise = exercisesPagingItems[index]
                if (exercise != null) {
                    ExerciseItem(
                        modifier = Modifier.fillMaxWidth(),
                        exercise = exercise,
                        onExerciseClick = {
                            onExerciseClick(exercise)
                        },
                        onExerciseGifClick = {
                            onExerciseGifClick(exercise)
                        }
                    )
                }
            }
        }

        if (exercisesPagingItems.loadState.refresh is LoadState.Loading) {
            items(5) {
                ShimmerExerciseItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                )
            }
        }

        if (exercisesPagingItems.loadState.append is LoadState.Loading) {
            items(1) {
                ShimmerExerciseItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(124.dp))
        }
    }
}

private suspend fun getCurrentErrorMessage(loadState: CombinedLoadStates): String? {
    return when {
        loadState.refresh is LoadState.Error -> {
            val error = (loadState.refresh as LoadState.Error).error
            getErrorMessage(error)
        }

        loadState.append is LoadState.Error -> {
            val error = (loadState.append as LoadState.Error).error
            getErrorMessage(error)
        }

        else -> null // No error in the current load state
    }
}

private suspend fun getErrorMessage(error: Throwable): String {
    return when (error) {
        is DataError -> {
            val errorMessageStringRes = error.toStringRes()
            val errorMessage = getString(errorMessageStringRes)
            errorMessage
        }

        else -> getString(Res.string.error_unknown)
    }
}