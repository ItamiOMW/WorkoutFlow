package com.itami.workout_flow.home.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.workout_flow.core.presentation.navigation.AppGraph
import com.itami.workout_flow.core.presentation.navigation.AppGraph.Workouts.WorkoutsScreen.WorkoutsLaunchMode
import com.itami.workout_flow.home.presentation.home.HomeScreenRoute

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    onShowGlobalSnackbar: (message: String) -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
) {
    navigation<AppGraph.Home>(startDestination = AppGraph.Home.HomeScreen) {
        composable<AppGraph.Home.HomeScreen> {
            HomeScreenRoute(
                onNavigateToProfile = {
                    navController.navigate(AppGraph.Profile.CurrentUserProfileScreen) {
                        popUpTo(AppGraph.Home.HomeScreen) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateToSearchWorkout = {
                    navController.navigate(
                        AppGraph.Workouts.WorkoutsScreen(launchMode = WorkoutsLaunchMode.Search.name)
                    ) {
                        popUpTo(AppGraph.Home.HomeScreen) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateToSearchWorkoutByType = { type ->
                    navController.navigate(AppGraph.Workouts.WorkoutsScreen(workoutType = type.name)) {
                        popUpTo(AppGraph.Home.HomeScreen) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateToRoutine = {

                },
                onNavigateToSignIn = {
                    navController.navigate(AppGraph.Auth.SignInScreen) {
                        popUpTo(AppGraph.Home.HomeScreen) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateToWorkoutDetails = { workoutId ->
                    navController.navigate(AppGraph.Workouts.WorkoutDetailsScreen(workoutId))
                },
                onShowLocalSnackbar = onShowLocalSnackbar,
            )
        }
    }
}