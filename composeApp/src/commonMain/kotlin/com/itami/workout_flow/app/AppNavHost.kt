package com.itami.workout_flow.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.itami.workout_flow.core.presentation.navigation.AppGraph
import com.itami.workout_flow.home.presentation.homeGraph
import com.itami.workout_flow.onboarding.presentation.onboardingGraph
import com.itami.workout_flow.profile.presentation.profileGraph
import com.itami.workout_flow.progress.presentation.progressGraph
import com.itami.workout_flow.workouts.presentation.workoutsGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startGraph: Any = AppGraph.Home,
    // Launched from root's scope and persists across different screen.
    onShowGlobalSnackbar: (message: String) -> Unit,
    // Launched from the screen's scope and will only live for the duration of that screen.
    onShowLocalSnackbar: suspend (message: String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startGraph
    ) {
        onboardingGraph(
            navController = navHostController,
            onShowGlobalSnackbar = onShowGlobalSnackbar,
            onShowLocalSnackbar = onShowLocalSnackbar
        )
        homeGraph(
            navController = navHostController,
            onShowGlobalSnackbar = onShowGlobalSnackbar,
            onShowLocalSnackbar = onShowLocalSnackbar
        )
        workoutsGraph(
            navController = navHostController,
            onShowGlobalSnackbar = onShowGlobalSnackbar,
            onShowLocalSnackbar = onShowLocalSnackbar
        )
        progressGraph(
            navController = navHostController,
            onShowGlobalSnackbar = onShowGlobalSnackbar,
            onShowLocalSnackbar = onShowLocalSnackbar
        )
        profileGraph(
            navController = navHostController,
            onShowGlobalSnackbar = onShowGlobalSnackbar,
            onShowLocalSnackbar = onShowLocalSnackbar
        )
    }
}