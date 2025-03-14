package com.itami.workout_flow.core.presentation.components.collapsing_top_bar

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlin.math.abs

/**
 * From [hh-histories-compose-custom-toolbar](https://github.com/hhru/hh-histories-compose-custom-toolbar)
 */
class CollapsingTopAppBarScrollBehavior(
    val state: CollapsingTopAppBarScrollState,
    val flingAnimationSpec: DecayAnimationSpec<Float>?,
) {
    val nestedScrollConnection = object : NestedScrollConnection {

        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            if (available.y > 0f) return Offset.Zero

            val prevHeightOffset = state.heightOffset
            state.heightOffset += available.y
            return if (prevHeightOffset != state.heightOffset) {
                available.copy(x = 0f)
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            state.contentOffset += consumed.y

            if (available.y < 0f || consumed.y < 0f) {
                val oldHeightOffset = state.heightOffset
                state.heightOffset += consumed.y
                return Offset(0f, state.heightOffset - oldHeightOffset)
            }

            if (consumed.y == 0f && available.y > 0) {
                state.contentOffset = 0f
            }

            if (available.y > 0f) {
                val oldHeightOffset = state.heightOffset
                state.heightOffset += available.y
                return Offset(0f, state.heightOffset - oldHeightOffset)
            }
            return Offset.Zero
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            var result = super.onPostFling(consumed, available)
            if (state.collapsedFraction > 0.01f && state.collapsedFraction < 1f) {
                result += flingToolbar(
                    state = state,
                    initialVelocity = available.y,
                    flingAnimationSpec = flingAnimationSpec
                )
                snapToolbar(state)
            }
            return result
        }

    }

}

private suspend fun flingToolbar(
    state: CollapsingTopAppBarScrollState,
    initialVelocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
): Velocity {
    var remainingVelocity = initialVelocity
    if (flingAnimationSpec != null && abs(initialVelocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = initialVelocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity
                if (abs(delta - consumed) > 0.5f) {
                    cancelAnimation()
                }
            }
    }
    return Velocity(0f, remainingVelocity)
}

private suspend fun snapToolbar(state: CollapsingTopAppBarScrollState) {
    if (state.heightOffset < 0 &&
        state.heightOffset > state.heightOffsetLimit
    ) {
        AnimationState(
            initialValue = state.heightOffset
        ).animateTo(
            targetValue = if (state.collapsedFraction < 0.5f) 0f else state.heightOffsetLimit,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
        ) {
            state.heightOffset = value
        }
    }
}

@Composable
fun rememberCollapsingTopAppBarScrollBehavior() = CollapsingTopAppBarScrollBehavior(
    state = rememberCollapsingTopAppBarScrollState(
        initialHeightOffsetLimit = -Float.MAX_VALUE
    ),
    flingAnimationSpec = rememberSplineBasedDecay()
)