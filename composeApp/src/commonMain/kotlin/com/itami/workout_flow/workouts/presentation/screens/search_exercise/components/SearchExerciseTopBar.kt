@file:OptIn(ExperimentalMaterial3Api::class)

package com.itami.workout_flow.workouts.presentation.screens.search_exercise.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.navigation.AppGraph.Workouts.SearchExerciseScreen.SearchExerciseLaunchMode
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.close_search
import workoutflow.composeapp.generated.resources.go_back
import workoutflow.composeapp.generated.resources.icon_arrow_back
import workoutflow.composeapp.generated.resources.icon_close
import workoutflow.composeapp.generated.resources.icon_filter_list
import workoutflow.composeapp.generated.resources.icon_search
import workoutflow.composeapp.generated.resources.open_filters
import workoutflow.composeapp.generated.resources.open_search
import workoutflow.composeapp.generated.resources.search_exercise
import workoutflow.composeapp.generated.resources.search_exercise_hint
import workoutflow.composeapp.generated.resources.select_exercise

@Composable
fun SearchExerciseTopBar(
    modifier: Modifier = Modifier,
    searchExerciseLaunchMode: SearchExerciseLaunchMode,
    searchQuery: String,
    showSearchQuery: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchQueryChange: (newValue: String) -> Unit,
    onNavigateBackClick: () -> Unit,
    onCloseSearchClick: () -> Unit,
    onOpenSearchClick: () -> Unit,
    onFilterClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = WorkoutFlowTheme.shapes.none,
        color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TopAppBar(
                modifier = Modifier,
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
                    scrolledContainerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
                    navigationIconContentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                    titleContentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                    actionIconContentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                ),
                title = {
                    if (showSearchQuery) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = searchQuery,
                            onValueChange = onSearchQueryChange,
                            textStyle = WorkoutFlowTheme.typography.bodyLarge,
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                unfocusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                focusedPlaceholderColor = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                                unfocusedPlaceholderColor = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            ),
                            maxLines = 1,
                            placeholder = {
                                Text(
                                    text = stringResource(Res.string.search_exercise_hint),
                                    style = WorkoutFlowTheme.typography.bodyLarge,
                                    color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                )
                            },
                        )
                    } else {
                        Text(
                            text = when(searchExerciseLaunchMode) {
                                SearchExerciseLaunchMode.Search -> {
                                    stringResource(Res.string.search_exercise)
                                }
                                SearchExerciseLaunchMode.Select -> {
                                    stringResource(Res.string.select_exercise)
                                }
                            },
                            style = WorkoutFlowTheme.typography.titleSmall,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBackClick
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_arrow_back),
                            contentDescription = stringResource(Res.string.go_back),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    if (showSearchQuery) {
                        IconButton(
                            onClick = onCloseSearchClick
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_close),
                                contentDescription = stringResource(Res.string.close_search),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = onOpenSearchClick
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_search),
                                contentDescription = stringResource(Res.string.open_search),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    IconButton(
                        onClick = onFilterClick
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_filter_list),
                            contentDescription = stringResource(Res.string.open_filters),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    }
}