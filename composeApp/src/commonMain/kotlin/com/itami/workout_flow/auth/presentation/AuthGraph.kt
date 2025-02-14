package com.itami.workout_flow.auth.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.workout_flow.auth.presentation.sign_in.SignInScreenRoute
import com.itami.workout_flow.core.presentation.navigation.AppGraph
import com.itami.workout_flow.core.presentation.utils.UiText

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    onShowGlobalSnackbar: (message: UiText) -> Unit,
    onShowLocalSnackbar: suspend (message: UiText) -> Unit,
) {
    navigation<AppGraph.Auth>(startDestination = AppGraph.Auth.SignInScreen) {
        composable<AppGraph.Auth.SignInScreen> {
            SignInScreenRoute(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onShowLocalSnackbar = onShowLocalSnackbar,
                onShowGlobalSnackbar = onShowGlobalSnackbar,
            )
        }
    }
}