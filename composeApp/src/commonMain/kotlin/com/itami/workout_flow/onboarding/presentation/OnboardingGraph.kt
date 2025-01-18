package com.itami.workout_flow.onboarding.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.workout_flow.core.presentation.navigation.AppGraph
import com.itami.workout_flow.onboarding.presentation.onboarding.OnboardingScreenRoot

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
    onShowGlobalSnackbar: (message: String) -> Unit,
    onShowLocalSnackbar: (message: String) -> Unit,
) {
    navigation<AppGraph.Onboarding>(startDestination = AppGraph.Onboarding.OnboardingScreen) {
        composable<AppGraph.Onboarding.OnboardingScreen> {
            OnboardingScreenRoot(
                onNavigateToHome = {
                    navController.navigate(AppGraph.Home) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}