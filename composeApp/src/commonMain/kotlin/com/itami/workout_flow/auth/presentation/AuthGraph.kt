package com.itami.workout_flow.auth.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.workout_flow.auth.presentation.sign_in.SignInScreenRoute
import com.itami.workout_flow.core.presentation.navigation.AppGraph

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    onShowGlobalSnackbar: (message: String) -> Unit,
    onShowLocalSnackbar: suspend (message: String) -> Unit,
) {
    navigation<AppGraph.Auth>(startDestination = AppGraph.Auth.SignInScreen) {
        composable<AppGraph.Auth.SignInScreen> {
            SignInScreenRoute(
                onNavigateBack = {
                    navController.navigateUp()
                },
            )
        }
    }
}