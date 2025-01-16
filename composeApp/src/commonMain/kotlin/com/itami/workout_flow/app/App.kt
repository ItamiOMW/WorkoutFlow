package com.itami.workout_flow.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.itami.workout_flow.core.domain.model.settings.Theme
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    WorkoutFlowTheme(
        theme = Theme.SYSTEM_THEME
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hello, WORLD!",
                style = WorkoutFlowTheme.typography.labelLarge
            )
            Text(
                text = "Hello, WORLD!",
                style = WorkoutFlowTheme.typography.bodyLarge
            )
        }
    }
}