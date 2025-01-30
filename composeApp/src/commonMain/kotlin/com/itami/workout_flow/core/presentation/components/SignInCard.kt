package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.enable_automatic_backups
import workoutflow.composeapp.generated.resources.hide
import workoutflow.composeapp.generated.resources.icon_close
import workoutflow.composeapp.generated.resources.icon_sign_in
import workoutflow.composeapp.generated.resources.sign_in

@Composable
fun SignInCard(
    modifier: Modifier = Modifier,
    onHide: (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
        contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
        shape = WorkoutFlowTheme.shapes.small,
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WorkoutFlowTheme.padding.default),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconToText(
                    iconPainter = painterResource(Res.drawable.icon_sign_in),
                    iconBrush = WorkoutFlowTheme.colors.brandColors.primaryGdVert,
                    text = stringResource(Res.string.sign_in),
                    textStyle = WorkoutFlowTheme.typography.labelLarge,
                    textColor = WorkoutFlowTheme.colors.surfaceColors.onSurface
                )
                if (onHide != null) {
                    IconButton(
                        onClick = onHide,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(Res.drawable.icon_close),
                            contentDescription = stringResource(Res.string.hide),
                            tint = WorkoutFlowTheme.colors.surfaceColors.onSurface
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .background(WorkoutFlowTheme.colors.surfaceColors.surfaceLow)
                    .fillMaxWidth()
                    .padding(WorkoutFlowTheme.padding.default),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = stringResource(Res.string.enable_automatic_backups),
                    style = WorkoutFlowTheme.typography.bodyMedium,
                    color = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}