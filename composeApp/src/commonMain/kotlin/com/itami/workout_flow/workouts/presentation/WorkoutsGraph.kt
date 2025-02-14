package com.itami.workout_flow.workouts.presentation

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

fun NavGraphBuilder.workoutsGraph(
    navController: NavHostController,
    onShowGlobalSnackbar: (message: UiText) -> Unit,
    onShowLocalSnackbar: suspend (message: UiText) -> Unit,
) {
    navigation<AppGraph.Workouts>(startDestination = AppGraph.Workouts.WorkoutsScreen()) {
        composable<AppGraph.Workouts.WorkoutsScreen> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Workouts Screen")
            }
        }
    }
}