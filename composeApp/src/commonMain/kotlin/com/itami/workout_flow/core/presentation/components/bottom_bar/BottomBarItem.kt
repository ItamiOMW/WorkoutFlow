package com.itami.workout_flow.core.presentation.components.bottom_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.components.GradientIcon
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun RowScope.BottomBarItem(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    selected: Boolean,
    title: String? = null,
    iconColor: Color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    selectedIconGradient: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    onClick: () -> Unit,
) {
    Surface(
        color = Color.Transparent,
        contentColor = iconColor,
        modifier = Modifier
            .clickable(
                onClick = { onClick() },
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = 30.dp),
            )
            .weight(1f),
    ) {
        Box(
            modifier = modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) {
                GradientIcon(
                    iconPainter = iconPainter,
                    brush = selectedIconGradient,
                    contentDescription = title,
                )
            } else {
                Icon(
                    painter = iconPainter,
                    contentDescription = title,
                    tint = iconColor,
                )
            }
        }
    }
}