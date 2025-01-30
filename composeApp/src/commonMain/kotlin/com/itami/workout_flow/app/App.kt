package com.itami.workout_flow.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.setSingletonImageLoaderFactory
import com.itami.workout_flow.app.utils.BottomBarNavItem
import com.itami.workout_flow.core.presentation.coil.ImageLoaderFactory
import com.itami.workout_flow.core.presentation.components.bottom_bar.AnimatedBottomBar
import com.itami.workout_flow.core.presentation.components.bottom_bar.BottomBarItem
import com.itami.workout_flow.core.presentation.navigation.AppGraph
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun App(
    appViewModel: AppViewModel = koinViewModel()
) {
    setSingletonImageLoaderFactory { context ->
        ImageLoaderFactory.getAsyncImageLoader(context)
    }

    val theme by appViewModel.theme.collectAsStateWithLifecycle()
    WorkoutFlowTheme(theme = theme) {
        val navController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val hazeState = remember { HazeState() }

        Scaffold(
            containerColor = WorkoutFlowTheme.colors.backgroundColors.background,
            contentColor = WorkoutFlowTheme.colors.backgroundColors.onBackground,
            snackbarHost = {
                SnackbarHost(
                    modifier = Modifier,
                    hostState = snackbarHostState,
                ) { snackbarData ->
                    Snackbar(
                        modifier = Modifier,
                        snackbarData = snackbarData,
                        containerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
                        contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        dismissActionContentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        shape = WorkoutFlowTheme.shapes.small,
                    )
                }
            },
            bottomBar = {
                AppBottomBar(
                    navController = navController,
                    hazeState = hazeState
                )
            }
        ) {
            AppNavHost(
                modifier = Modifier
                    .navigationBarsPadding()
                    .hazeSource(hazeState),
                navHostController = navController,
                startGraph = AppGraph.Home,
                onShowLocalSnackbar = { message ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        withDismissAction = true,
                    )
                },
                onShowGlobalSnackbar = { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = message,
                            withDismissAction = true
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun AppBottomBar(
    navController: NavHostController,
    hazeState: HazeState,
) {
    val bottomNavItems = remember {
        listOf(
            BottomBarNavItem.Home,
            BottomBarNavItem.Workouts,
            BottomBarNavItem.Progress,
            BottomBarNavItem.Profile
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val selectedItemIndex = bottomNavItems.indexOfFirst { item ->
        navBackStackEntry?.destination
            ?.hierarchy
            ?.any { it.hasRoute(item.screen::class) } == true
    }

    AnimatedVisibility(
        visible = selectedItemIndex != -1,
        enter = fadeIn() + expandIn(expandFrom = Alignment.BottomCenter),
        exit = shrinkOut(shrinkTowards = Alignment.BottomCenter) + fadeOut(),
    ) {
        val hazeBackgroundColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh.copy(
            alpha = if (WorkoutFlowTheme.isDarkTheme) 0.99f else 0.97f
        )
        AnimatedBottomBar(
            modifier = Modifier
                .hazeEffect(hazeState) {
                    backgroundColor = hazeBackgroundColor
                }
                .navigationBarsPadding()
                .fillMaxWidth(),
            containerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh.copy(
                alpha = if (WorkoutFlowTheme.isDarkTheme) 0.99f else 0.97f
            ),
            dividerColor = if (WorkoutFlowTheme.isDarkTheme) {
                WorkoutFlowTheme.colors.outlineColors.outlineLow
            } else {
                WorkoutFlowTheme.colors.outlineColors.outlineHigh
            },
            itemSize = bottomNavItems.size,
            selectedItem = selectedItemIndex,
        ) {
            bottomNavItems.forEachIndexed { index, item ->
                val selected = selectedItemIndex == index
                BottomBarItem(
                    iconPainter = painterResource(resource = item.iconRes),
                    title = stringResource(resource = item.titleRes),
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(item.screen) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                )
            }
        }
    }
}