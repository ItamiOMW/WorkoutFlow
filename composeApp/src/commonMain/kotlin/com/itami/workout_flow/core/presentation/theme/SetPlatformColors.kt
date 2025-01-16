package com.itami.workout_flow.core.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun SetPlatformColors(
    statusBarColor: Color,
    navBarColor: Color,
    isDarkTheme: Boolean
)