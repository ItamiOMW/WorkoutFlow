package com.itami.workout_flow.core.presentation.navigation.utils

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator

typealias NavResultCallback<T> = (T) -> Unit

private const val NAV_RESULT_CALLBACK_KEY = "NavResultCallbackKey"


fun <T> NavController.setNavResultCallback(callback: NavResultCallback<T>) {
    currentBackStackEntry?.savedStateHandle?.set(NAV_RESULT_CALLBACK_KEY, callback)
}


fun <T> NavController.getNavResultCallback(): NavResultCallback<T>? {
    return previousBackStackEntry?.savedStateHandle?.remove(NAV_RESULT_CALLBACK_KEY)
}


fun <T> NavController.popBackStackWithResult(result: T) {
    getNavResultCallback<T>()?.invoke(result)
    popBackStack()
}


fun <T : Any> NavController.navigateForResult(
    route: T,
    navResultCallback: NavResultCallback<T>,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    setNavResultCallback(navResultCallback)
    navigate(route, navOptions, navigatorExtras)
}

fun <T> NavController.navigateForResult(
    route: Any,
    navResultCallback: NavResultCallback<T>,
    builder: NavOptionsBuilder.() -> Unit,
) {
    setNavResultCallback(navResultCallback)
    navigate(route, builder)
}