package com.itami.workout_flow.workouts.presentation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.itami.workout_flow.core.presentation.navigation.AppGraph
import com.itami.workout_flow.core.presentation.navigation.AppGraph.Workouts.SearchExerciseScreen.SearchExerciseLaunchMode
import com.itami.workout_flow.core.presentation.navigation.AppGraph.Workouts.WorkoutsScreen.WorkoutsLaunchMode
import com.itami.workout_flow.core.presentation.navigation.utils.sharedKoinViewModel
import com.itami.workout_flow.workouts.presentation.screens.search_exercise.SearchExerciseScreenRoute
import com.itami.workout_flow.workouts.presentation.screens.workout_details.WorkoutDetailsScreenRoute
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.WorkoutEditorAction
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.WorkoutEditorScreenRoute
import com.itami.workout_flow.workouts.presentation.screens.workout_editor.WorkoutEditorViewModel
import com.itami.workout_flow.workouts.presentation.screens.workouts.WorkoutsScreenRoute
import com.itami.workout_flow.workouts.presentation.view_model.SharedExerciseViewModel
import org.koin.compose.viewmodel.koinViewModel

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
        composable<AppGraph.Workouts.WorkoutEditorScreen> { backstackEntry ->
            val sharedExerciseViewModel =
                backstackEntry.sharedKoinViewModel<SharedExerciseViewModel>(navController)
            val workoutEditorViewModel = koinViewModel<WorkoutEditorViewModel>()

            val selectedExercise by sharedExerciseViewModel.selectedExercise.collectAsStateWithLifecycle()

            LaunchedEffect(selectedExercise) {
                selectedExercise?.let { exercise ->
                    workoutEditorViewModel.onAction(WorkoutEditorAction.AddExerciseNavResult(exercise))
                    sharedExerciseViewModel.onSelectExercise(null)
                }
            }

            WorkoutEditorScreenRoute(
                onNavigateToSearchExercise = {
                    navController.navigate(
                        AppGraph.Workouts.SearchExerciseScreen(
                            launchMode = SearchExerciseLaunchMode.Select.name
                        )
                    )
                },
                onNavigateBack = navController::navigateUp,
                onShowLocalSnackbar = onShowLocalSnackbar,
                viewModel = workoutEditorViewModel
            )
        }
        composable<AppGraph.Workouts.SearchExerciseScreen> { backstack ->
            val sharedExerciseViewModel = backstack.sharedKoinViewModel<SharedExerciseViewModel>(navController)

            LaunchedEffect(true) {
                sharedExerciseViewModel.onSelectExercise(null)
            }

            SearchExerciseScreenRoute(
                onNavigateToExerciseDetails = {
                    // TODO navigate to exercise details
                },
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateBackWithResult = { exercise ->
                    sharedExerciseViewModel.onSelectExercise(exercise)
                    navController.navigateUp()
                },
                onShowLocalSnackbar = onShowLocalSnackbar,
            )
        }
    }
}