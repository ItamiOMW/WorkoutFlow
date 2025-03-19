package com.itami.workout_flow.core.presentation.components.switch

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush

@Immutable
class GradientSwitchColors
constructor(
    val checkedThumbBrush: Brush,
    val checkedTrackBrush: Brush,
    val checkedBorderBrush: Brush,
    val uncheckedThumbBrush: Brush,
    val uncheckedTrackBrush: Brush,
    val uncheckedBorderBrush: Brush,
    val disabledCheckedThumbBrush: Brush = checkedThumbBrush,
    val disabledCheckedTrackBrush: Brush = checkedTrackBrush,
    val disabledCheckedBorderBrush: Brush = checkedBorderBrush,
    val disabledUncheckedThumbBrush: Brush = uncheckedThumbBrush,
    val disabledUncheckedTrackBrush: Brush = uncheckedTrackBrush,
    val disabledUncheckedBorderBrush: Brush = uncheckedBorderBrush,
) {
    @Stable
    internal fun thumbBrush(enabled: Boolean, checked: Boolean): Brush =
        if (enabled) {
            if (checked) checkedThumbBrush else uncheckedThumbBrush
        } else {
            if (checked) disabledCheckedThumbBrush else disabledUncheckedThumbBrush
        }

    /**
     * Represents the color used for the switch's track, depending on [enabled] and [checked].
     *
     * @param enabled whether the [GradientSwitch] is enabled or not
     * @param checked whether the [GradientSwitch] is checked or not
     */
    @Stable
    internal fun trackBrush(enabled: Boolean, checked: Boolean): Brush =
        if (enabled) {
            if (checked) checkedTrackBrush else uncheckedTrackBrush
        } else {
            if (checked) disabledCheckedTrackBrush else disabledUncheckedTrackBrush
        }

    /**
     * Represents the color used for the switch's border, depending on [enabled] and [checked].
     *
     * @param enabled whether the [GradientSwitch] is enabled or not
     * @param checked whether the [GradientSwitch] is checked or not
     */
    @Stable
    internal fun borderBrush(enabled: Boolean, checked: Boolean): Brush =
        if (enabled) {
            if (checked) checkedBorderBrush else uncheckedBorderBrush
        } else {
            if (checked) disabledCheckedBorderBrush else disabledUncheckedBorderBrush
        }
}