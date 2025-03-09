package com.itami.workout_flow.core.presentation.components.tab_bar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun TabBar(
    modifier: Modifier = Modifier,
    containerColor: Color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
    contentColor: Color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    selectedPage: Int? = null,
    pageCount: Int? = null,
    indicatorBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    indicatorHeight: Dp = 3.dp,
    indicatorShape: Shape = WorkoutFlowTheme.shapes.extraSmall,
    animationSpec: AnimationSpec<Dp> = spring(
        dampingRatio = 1f,
        stiffness = Spring.StiffnessMediumLow
    ),
    contentPadding: PaddingValues = PaddingValues(horizontal = WorkoutFlowTheme.padding.default),
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                selectedPage?.let {
                    pageCount?.let {
                        val maxWidth = this.maxWidth
                        val indicatorOffset: Dp by animateDpAsState(
                            targetValue = (maxWidth / (pageCount.takeIf { it != 0 } ?: 1)) * selectedPage,
                            animationSpec = animationSpec,
                            label = "Indicator",
                        )

                        LineIndicator(
                            indicatorOffset = indicatorOffset,
                            arraySize = pageCount.takeIf { it != 0 } ?: 1,
                            indicatorHeight = indicatorHeight,
                            indicatorBrush = indicatorBrush,
                            modifier = Modifier.align(Alignment.BottomStart),
                            indicatorShape = indicatorShape,
                        )
                    }
                }
            }
        }
    }
}