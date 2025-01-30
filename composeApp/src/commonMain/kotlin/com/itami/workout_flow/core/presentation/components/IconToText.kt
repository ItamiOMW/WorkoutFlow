package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun IconToText(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    iconSize: Dp = 24.dp,
    iconColor: Color = WorkoutFlowTheme.colors.backgroundColors.onBackground,
    iconDescription: String? = null,
    text: String,
    textStyle: TextStyle = WorkoutFlowTheme.typography.labelLarge,
    textColor: Color = iconColor,
    maxLines: Int = 1,
    textOverflow: TextOverflow = TextOverflow.Ellipsis,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
    ) {
        Icon(
            painter = iconPainter,
            tint = iconColor,
            contentDescription = iconDescription ?: text,
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            maxLines = maxLines,
            overflow = textOverflow
        )
    }
}

@Composable
fun IconToText(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    iconSize: Dp = 24.dp,
    iconBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdVert,
    iconDescription: String? = null,
    text: String,
    textStyle: TextStyle = WorkoutFlowTheme.typography.labelLarge,
    textColor: Color = WorkoutFlowTheme.colors.backgroundColors.onBackground,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
    ) {
        GradientIcon(
            iconPainter = iconPainter,
            brush = iconBrush,
            contentDescription = iconDescription ?: text,
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}