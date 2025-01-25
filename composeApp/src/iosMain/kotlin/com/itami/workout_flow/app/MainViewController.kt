package com.itami.workout_flow.app

import androidx.compose.ui.window.ComposeUIViewController
import com.itami.workout_flow.app.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }