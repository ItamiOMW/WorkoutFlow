package com.itami.workout_flow.core.presentation.components.button

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Copy of [androidx.compose.material3.ButtonColors] that supports Brush
 */
@Immutable
class GradientButtonColors
constructor(
    val containerBrush: Brush,
    val contentColor: Color,
    val disabledContainerBrush: Brush = containerBrush,
    val disabledContentColor: Color = contentColor,
) {

    @Stable
    internal fun containerColor(enabled: Boolean): Brush =
        if (enabled) containerBrush else disabledContainerBrush

    @Stable
    internal fun contentColor(enabled: Boolean): Color =
        if (enabled) contentColor else disabledContentColor

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is GradientButtonColors) return false

        if (containerBrush != other.containerBrush) return false
        if (contentColor != other.contentColor) return false
        if (disabledContainerBrush != other.disabledContainerBrush) return false
        if (disabledContentColor != other.disabledContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = containerBrush.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledContainerBrush.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }
}