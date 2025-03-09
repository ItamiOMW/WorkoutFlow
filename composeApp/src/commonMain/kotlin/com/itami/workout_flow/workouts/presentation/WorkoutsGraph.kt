package com.itami.workout_flow.workouts.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.workout_flow.core.presentation.navigation.AppGraph
import com.itami.workout_flow.workouts.presentation.screens.workouts.WorkoutsScreenRoute

fun NavGraphBuilder.workoutsGraph(
    navController: NavHostController,
    onShowGlobalSnackbar: (message: String) -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
) {
    navigation<AppGraph.Workouts>(startDestination = AppGraph.Workouts.WorkoutsScreen()) {
        composable<AppGraph.Workouts.WorkoutsScreen> {
            WorkoutsScreenRoute(
                onNavigateToWorkoutDetails = { workoutId ->
                    navController.navigate(AppGraph.Workouts.WorkoutDetailsScreen(workoutId = workoutId)) {
                        popUpTo(AppGraph.Workouts.WorkoutsScreen()) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onNavigateToWorkoutEditor = {
                    navController.navigate(AppGraph.Workouts.WorkoutEditorScreen(workoutId = null)) {
                        popUpTo(AppGraph.Workouts.WorkoutsScreen()) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onShowLocalSnackbar = onShowLocalSnackbar
            )
        }
    }
}