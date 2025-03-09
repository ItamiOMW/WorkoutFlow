package com.itami.workout_flow.core.presentation.components.tab_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
internal fun LineIndicator(
    indicatorOffset: Dp,
    arraySize: Int,
    indicatorBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    indicatorHeight: Dp,
    modifier: Modifier,
    indicatorShape: Shape,
) {
    Box(
        modifier = modifier
            .height(indicatorHeight)
            .fillMaxWidth(1f / arraySize)
            .offset(x = indicatorOffset)
            .clip(shape = indicatorShape)
            .background(brush = indicatorBrush, shape = indicatorShape),
    )
}