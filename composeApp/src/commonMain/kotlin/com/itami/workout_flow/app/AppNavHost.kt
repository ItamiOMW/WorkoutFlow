package com.itami.workout_flow.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.itami.workout_flow.core.presentation.navigation.AppGraph

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

    }
}