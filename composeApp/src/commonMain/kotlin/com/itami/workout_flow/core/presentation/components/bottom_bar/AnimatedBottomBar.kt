package com.itami.workout_flow.core.presentation.components.bottom_bar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun AnimatedBottomBar(
    modifier: Modifier = Modifier,
    bottomBarHeight: Dp = 74.dp,
    containerColor: Color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
    contentColor: Color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    dividerColor: Color = WorkoutFlowTheme.colors.outlineColors.outlineHigh,
    containerShape: Shape = WorkoutFlowTheme.shapes.none,
    selectedItem: Int? = null,
    itemSize: Int? = null,
    indicatorGradient: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdVert,
    animationSpec: AnimationSpec<Dp> = spring(
        dampingRatio = 1f,
        stiffness = Spring.StiffnessMediumLow
    ),
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        color = containerColor,
        contentColor = contentColor,
        modifier = modifier,
        shape = containerShape,
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = dividerColor
        )
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            selectedItem?.let {
                itemSize?.let {
                    val maxWidth = this.maxWidth
                    val indicatorOffset: Dp by animateDpAsState(
                        targetValue = (maxWidth / (itemSize.takeIf { it != 0 } ?: 1)) * selectedItem,
                        animationSpec = animationSpec,
                        label = "Indicator",
                    )

                    WormIndicator(
                        indicatorOffset = indicatorOffset,
                        indicatorGradient = indicatorGradient,
                        modifier = Modifier.height(bottomBarHeight),
                        itemWidth = maxWidth / (itemSize.takeIf { it != 0 } ?: 1),
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomBarHeight)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content,
        )
    }
}