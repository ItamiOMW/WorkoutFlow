package com.itami.workout_flow.home.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme.isDarkTheme

@Composable
internal fun BoxScope.HomeBlurredCircle() {
    val circleColor = if (isDarkTheme) {
        Color(0XFF4535A4).copy(alpha = 0.15f)
    } else {
        Color(0XFF3F52C0).copy(alpha = 0.3f)
    }

    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .size(400.dp)
            .offset((-100).dp, (-100).dp)
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            circleColor,
                            Color.Transparent
                        )
                    ),
                    radius = size.minDimension / 2
                )
            }
            .blur(radius = 100.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded) // Add blur effect
    )
}