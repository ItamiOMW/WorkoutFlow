package com.itami.workout_flow.app

import androidx.compose.runtime.Composable
import com.itami.workout_flow.core.domain.model.settings.Theme
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    WorkoutFlowTheme(
        theme = Theme.SYSTEM_THEME
    ) {

    }
}