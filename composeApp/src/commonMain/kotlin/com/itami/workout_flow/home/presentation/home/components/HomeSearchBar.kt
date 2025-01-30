package com.itami.workout_flow.home.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.icon_search
import workoutflow.composeapp.generated.resources.search_hint
import workoutflow.composeapp.generated.resources.search_icon

@Composable
fun HomeSearchBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        color = if (WorkoutFlowTheme.isDarkTheme) {
            WorkoutFlowTheme.colors.surfaceColors.surfaceHigh
        } else {
            WorkoutFlowTheme.colors.brandColors.secondary
        },
        contentColor = if (WorkoutFlowTheme.isDarkTheme) {
            WorkoutFlowTheme.colors.surfaceColors.onSurface
        } else {
            WorkoutFlowTheme.colors.brandColors.onSecondary
        },
        shape = WorkoutFlowTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = WorkoutFlowTheme.padding.default,
                    horizontal = WorkoutFlowTheme.padding.medium
                ),
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(Res.drawable.icon_search),
                contentDescription = stringResource(Res.string.search_icon),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(Res.string.search_hint),
                style = WorkoutFlowTheme.typography.labelLarge,
            )
        }
    }

}