package com.itami.workout_flow.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.itami.workout_flow.core.domain.model.settings.Theme

private val LightColorScheme = WorkoutFlowColors(
    brandColors = BrandColors(
        primaryGdVert = Brush.verticalGradient(
            colors = listOf(
                Color(0XFF523EC1),
                Color(0XFF8ABFFF)
            )
        ),
        primaryGdHoriz = Brush.horizontalGradient(
            colors = listOf(
                Color(0XFF523EC1),
                Color(0XFF8ABFFF)
            )
        ),
        onPrimary = Color(0XFFFFFFFF),
        primaryGdVertContainer = Brush.verticalGradient(
            colors = listOf(
                Color(0XFFDDDAEE),
                Color(0XFFE5EDF6)
            )
        ),
        primaryGdHorizContainer = Brush.horizontalGradient(
            colors = listOf(
                Color(0XFFDDDAEE),
                Color(0XFFE5EDF6)
            )
        ),
        onPrimaryContainer = Color(0XFF191A1F),
        secondary = Color(0XFF191A1F),
        onSecondary = Color(0XFFD9D9D9)
    ),
    backgroundColors = BackgroundColors(
        background = Color(0XFFF8F9FC),
        onBackground = Color(0XFF191A1F),
        onBackgroundVariant = Color(0XFF444755)
    ),
    surfaceColors = SurfaceColors(
        surfaceHigh = Color(0XFFFFFFFF),
        surfaceLow = Color(0XFFFAFAFA),
        onSurface = Color(0XFF191A1F),
        onSurfaceVariant = Color(0XFF444755),
    ),
    outlineColors = OutlineColors(
        outlineHigh = Color(0XFFE6E6E6),
        outlineLow = Color(0XFFD9D9D9)
    ),
    dangerColors = DangerColors(
        danger = Color(0XFFFC4153),
        onDanger = Color(0XFFFFFFFF)
    ),
    generalColors = GeneralColors(
        pink = Color(0XFFF53D74),
        yellow = Color(0XFFF1EA38),
    )
)

private val DarkColorScheme = WorkoutFlowColors(
    brandColors = BrandColors(
        primaryGdVert = Brush.verticalGradient(
            colors = listOf(
                Color(0XFF4535A4),
                Color(0XFF75B4FF)
            )
        ),
        primaryGdHoriz = Brush.horizontalGradient(
            colors = listOf(
                Color(0XFF4535A4),
                Color(0XFF75B4FF)
            )
        ),
        onPrimary = Color(0XFFE6E6E6),
        primaryGdVertContainer = Brush.verticalGradient(
            colors = listOf(
                Color(0XFF100E27),
                Color(0XFF1B2635)
            )
        ),
        primaryGdHorizContainer = Brush.horizontalGradient(
            colors = listOf(
                Color(0XFF100E27),
                Color(0XFF1B2635)
            )
        ),
        onPrimaryContainer = Color(0XFFD9D9D9),
        secondary = Color(0XFFD9D9D9),
        onSecondary = Color(0XFF191A1F)
    ),
    backgroundColors = BackgroundColors(
        background = Color(0XFF05060A),
        onBackground = Color(0XFFD9D9D9),
        onBackgroundVariant = Color(0XFF8E92A4)
    ),
    surfaceColors = SurfaceColors(
        surfaceHigh = Color(0XFF14171E),
        surfaceLow = Color(0XFF101318),
        onSurface = Color(0XFFE3E4E8),
        onSurfaceVariant = Color(0XFF9CA0AF),
    ),
    outlineColors = OutlineColors(
        outlineHigh = Color(0XFF444755),
        outlineLow = Color(0XFF2D2F39)
    ),
    dangerColors = DangerColors(
        danger = Color(0XFFFF4253),
        onDanger = Color(0XFFE6E6E6)
    ),
    generalColors = GeneralColors(
        pink = Color(0XFFF53D74),
        yellow = Color(0XFFF1EA38),
    )
)

private val typography = WorkoutFlowTypography()
private val shapes = WorkoutFlowShapes()
private val padding = WorkoutFlowPadding()

private val LocalWorkoutFlowColorsProvider = staticCompositionLocalOf { LightColorScheme }
private val LocalWorkoutFlowTypographyProvider = staticCompositionLocalOf { typography }
private val LocalWorkoutFlowShapesProvider = staticCompositionLocalOf { shapes }
private val LocalWorkoutFlowPaddingProvider = staticCompositionLocalOf { padding }
private val LocalIsDarkThemeProvider = staticCompositionLocalOf { false }

data object WorkoutFlowTheme {

    val colors: WorkoutFlowColors
        @Composable
        get() = LocalWorkoutFlowColorsProvider.current

    val typography: WorkoutFlowTypography
        @Composable
        get() = LocalWorkoutFlowTypographyProvider.current

    val shapes: WorkoutFlowShapes
        @Composable
        get() = LocalWorkoutFlowShapesProvider.current

    val padding: WorkoutFlowPadding
        @Composable
        get() = LocalWorkoutFlowPaddingProvider.current

    val isDarkTheme: Boolean
        @Composable
        get() = LocalIsDarkThemeProvider.current

}

@Composable
fun WorkoutFlowTheme(
    theme: Theme = Theme.SYSTEM_THEME,
    content: @Composable () -> Unit,
) {
    val isDarkTheme = when (theme) {
        Theme.DARK_THEME -> true
        Theme.LIGHT_THEME -> false
        Theme.SYSTEM_THEME -> isSystemInDarkTheme()
    }

    SetPlatformColors(
        statusBarColor = if (isDarkTheme) {
            DarkColorScheme.surfaceColors.surfaceHigh
        } else {
            LightColorScheme.surfaceColors.surfaceHigh
        },
        navBarColor = if (isDarkTheme) {
            DarkColorScheme.surfaceColors.surfaceHigh
        } else {
            LightColorScheme.surfaceColors.surfaceHigh
        },
        isDarkTheme = isDarkTheme
    )

    CompositionLocalProvider(
        LocalWorkoutFlowColorsProvider provides if (isDarkTheme) DarkColorScheme else LightColorScheme,
        LocalWorkoutFlowTypographyProvider provides WorkoutFLowTypography(),
        LocalWorkoutFlowShapesProvider provides shapes,
        LocalWorkoutFlowPaddingProvider provides padding,
        LocalIsDarkThemeProvider provides isDarkTheme,
        content = content
    )
}