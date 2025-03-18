package com.itami.workout_flow.workouts.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.itami.workout_flow.core.presentation.navigation.AppGraph
import com.itami.workout_flow.core.presentation.navigation.AppGraph.Workouts.WorkoutsScreen.WorkoutsLaunchMode
import com.itami.workout_flow.workouts.presentation.screens.workout_details.WorkoutDetailsScreenRoute
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.WorkoutEditorScreenRoute
import com.itami.workout_flow.workouts.presentation.screens.workouts.WorkoutsScreenRoute

fun NavGraphBuilder.workoutsGraph(
    navController: NavHostController,
    onShowGlobalSnackbar: (message: String) -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
) {
    navigation<AppGraph.Workouts>(startDestination = AppGraph.Workouts.WorkoutsScreen()) {
        composable<AppGraph.Workouts.WorkoutsScreen> { backstack ->
            val workoutsScreen = backstack.toRoute<AppGraph.Workouts.WorkoutsScreen>()
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
                workoutsLaunchMode = WorkoutsLaunchMode.valueOf(workoutsScreen.launchMode),
                onShowLocalSnackbar = onShowLocalSnackbar
            )
        }
        composable<AppGraph.Workouts.WorkoutDetailsScreen> { _ ->
            WorkoutDetailsScreenRoute(
                onNavigateToEditWorkout = { workoutId ->
                    navController.navigate(AppGraph.Workouts.WorkoutEditorScreen(workoutId = workoutId)) {
                        popUpTo(AppGraph.Workouts.WorkoutDetailsScreen(workoutId)) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onNavigateToUserProfile = {
                    // TODO navigate to user profile
                },
                onNavigateBack = navController::navigateUp,
            )
        }
        composable<AppGraph.Workouts.WorkoutDetailsScreen> { _ ->
            WorkoutEditorScreenRoute(
                onNavigateToSearchExercise = { navResultCallback ->
                    // TODO navigate to search exercise
                },
                onNavigateBack = navController::navigateUp,
                onShowLocalSnackbar = onShowLocalSnackbar,
            )
        }
    }
}