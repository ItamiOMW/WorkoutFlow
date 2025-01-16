package com.itami.workout_flow

import androidx.compose.ui.window.ComposeUIViewController
import com.itami.workout_flow.app.App
import com.itami.workout_flow.app.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }