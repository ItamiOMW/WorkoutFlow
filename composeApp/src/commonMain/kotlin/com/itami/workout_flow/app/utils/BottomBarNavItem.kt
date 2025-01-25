package com.itami.workout_flow.app.utils

import com.itami.workout_flow.core.presentation.navigation.AppGraph
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.home
import workoutflow.composeapp.generated.resources.icon_dumbell
import workoutflow.composeapp.generated.resources.icon_home
import workoutflow.composeapp.generated.resources.icon_person
import workoutflow.composeapp.generated.resources.icon_progress
import workoutflow.composeapp.generated.resources.profile
import workoutflow.composeapp.generated.resources.progress
import workoutflow.composeapp.generated.resources.workouts

sealed class BottomBarNavItem<T>(
    val titleRes: StringResource,
    val iconRes: DrawableResource,
    val screen: T,
) {
    data object Home : BottomBarNavItem<AppGraph.Home.HomeScreen>(
        titleRes = Res.string.home,
        iconRes = Res.drawable.icon_home,
        screen = AppGraph.Home.HomeScreen,
    )

    data object Workouts : BottomBarNavItem<AppGraph.Workouts.WorkoutsScreen>(
        titleRes = Res.string.workouts,
        iconRes = Res.drawable.icon_dumbell,
        screen = AppGraph.Workouts.WorkoutsScreen(),
    )

    data object Progress : BottomBarNavItem<AppGraph.Progress.ProgressScreen>(
        titleRes = Res.string.progress,
        iconRes = Res.drawable.icon_progress,
        screen = AppGraph.Progress.ProgressScreen,
    )

    data object Profile : BottomBarNavItem<AppGraph.Profile.CurrentUserProfileScreen>(
        titleRes = Res.string.profile,
        iconRes = Res.drawable.icon_person,
        screen = AppGraph.Profile.CurrentUserProfileScreen,
    )
}