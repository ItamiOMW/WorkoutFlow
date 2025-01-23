package com.itami.workout_flow.core.presentation.components.bottom_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun WormIndicator(
    indicatorOffset: Dp,
    indicatorGradient: Brush,
    modifier: Modifier,
    itemWidth: Dp,
) {
    Box(modifier = modifier.width(itemWidth)) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
                .customWormTransition(indicatorOffset, indicatorGradient, itemWidth)
                .height(4.dp)
                .fillMaxWidth(0.25f)
        )
    }
}

private fun Modifier.customWormTransition(
    offset: Dp,
    indicatorGradient: Brush,
    itemWidth: Dp,
) = composed {
    drawWithContent {
        val distance = itemWidth.roundToPx()
        val scrollPosition = (offset.toPx().div(distance))
        val wormOffset = (scrollPosition % 1) * 2

        val xPos = scrollPosition.toInt() * distance
        val head = xPos + distance * 0f.coerceAtLeast(wormOffset - 1)
        val tail = xPos + size.width + distance * 1f.coerceAtMost(wormOffset)

        val worm = RoundRect(
            head,
            0f,
            tail,
            size.height,
            CornerRadius(50f),
        )

        val path = Path().apply { addRoundRect(worm) }

        drawPath(path = path, brush = indicatorGradient)
    }
}