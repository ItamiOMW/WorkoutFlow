package com.itami.workout_flow.progress.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.workout_flow.core.presentation.navigation.AppGraph
import com.itami.workout_flow.core.presentation.utils.UiText

fun NavGraphBuilder.progressGraph(
    navController: NavHostController,
    onShowGlobalSnackbar: (message: UiText) -> Unit,
    onShowLocalSnackbar: suspend (message: UiText) -> Unit,
) {
    navigation<AppGraph.Progress>(startDestination = AppGraph.Progress.ProgressScreen) {
        composable<AppGraph.Progress.ProgressScreen> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Progress Screen")
            }
        }
    }
}