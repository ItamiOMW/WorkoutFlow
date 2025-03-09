package com.itami.workout_flow.workouts.presentation.screens.workouts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itami.workout_flow.core.domain.model.workout.WorkoutPreview
import com.itami.workout_flow.core.presentation.components.WorkoutPreviewItem
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import kotlinx.coroutines.flow.Flow

@Composable
internal fun WorkoutsCustomPagerScreenContent(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    customWorkoutsPreviewFlow: Flow<List<WorkoutPreview>>,
    onWorkoutPreviewClick: (WorkoutPreview) -> Unit,
) {
    val customWorkouts by customWorkoutsPreviewFlow.collectAsStateWithLifecycle(emptyList())

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.medium),
        contentPadding = PaddingValues(WorkoutFlowTheme.padding.default)
    ) {
        items(items = customWorkouts, key = { it.id }) { workoutPreview ->
            WorkoutPreviewItem(
                workoutPreview = workoutPreview,
                onClick = {
                    onWorkoutPreviewClick(workoutPreview)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
            )
        }

        item {
            Spacer(modifier = Modifier.height(124.dp))
        }
    }
}