package com.itami.workout_flow.workouts.presentation.screens.workouts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.components.tab_bar.TabBar
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.workouts.presentation.screens.workouts.WorkoutsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.close_search
import workoutflow.composeapp.generated.resources.icon_close
import workoutflow.composeapp.generated.resources.icon_filter_list
import workoutflow.composeapp.generated.resources.icon_search
import workoutflow.composeapp.generated.resources.icon_sort_vert
import workoutflow.composeapp.generated.resources.open_filters
import workoutflow.composeapp.generated.resources.open_search
import workoutflow.composeapp.generated.resources.open_sort
import workoutflow.composeapp.generated.resources.search_workouts_hint
import workoutflow.composeapp.generated.resources.workouts


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WorkoutsTopBar(
    pagerScreens: List<WorkoutsState.WorkoutsPagerScreen>,
    currentPageIndex: Int,
    searchQuery: String,
    showSearchQuery: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchQueryChange: (newValue: String) -> Unit,
    onCloseSearchClick: () -> Unit,
    onOpenSearchClick: () -> Unit,
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit,
    onPagerTabClick: (pageIndex: Int) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
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
                                    text = stringResource(Res.string.search_workouts_hint),
                                    style = WorkoutFlowTheme.typography.bodyLarge,
                                    color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                )
                            },
                        )
                    } else {
                        Text(
                            text = stringResource(Res.string.workouts),
                            style = WorkoutFlowTheme.typography.titleSmall,
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
                        onClick = onSortClick
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_sort_vert),
                            contentDescription = stringResource(Res.string.open_sort),
                            modifier = Modifier.size(24.dp)
                        )
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
            Spacer(Modifier.height(WorkoutFlowTheme.padding.tiny))
            TabBar(
                modifier = Modifier.fillMaxWidth(),
                selectedPage = currentPageIndex,
                pageCount = pagerScreens.size,
            ) {
                pagerScreens.forEachIndexed { index, page ->
                    Surface(
                        modifier = Modifier.weight(1f),
                        onClick = { onPagerTabClick(index) },
                        color = Color.Transparent,
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(vertical = WorkoutFlowTheme.padding.tiny)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(page.titleResId),
                                style = WorkoutFlowTheme.typography.labelLarge,
                                color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}