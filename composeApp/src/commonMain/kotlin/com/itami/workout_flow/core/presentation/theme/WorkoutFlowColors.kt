package com.itami.workout_flow.core.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class WorkoutFlowColors(
    val brandColors: BrandColors,
    val backgroundColors: BackgroundColors,
    val surfaceColors: SurfaceColors,
    val outlineColors: OutlineColors,
    val dangerColors: DangerColors,
    val generalColors: GeneralColors,
)

// Gd - Gradient
// Vert - Vertical
// Horiz - Horizontal
@Immutable
data class BrandColors(
    val primaryGdVert: Brush,
    val primaryGdHoriz: Brush,
    val onPrimary: Color,
    val primaryGdVertContainer: Brush,
    val primaryGdHorizContainer: Brush,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
)

@Immutable
data class BackgroundColors(
    val background: Color,
    val onBackground: Color,
    val onBackgroundVariant: Color,
)

@Immutable
data class SurfaceColors(
    val surfaceHigh: Color,
    val surfaceLow: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
)

@Immutable
data class OutlineColors(
    val outlineHigh: Color,
    val outlineLow: Color,
)

@Immutable
data class DangerColors(
    val danger: Color,
    val onDanger: Color,
)

@Immutable
data class GeneralColors(
    val pink: Color,
    val yellow: Color,
)