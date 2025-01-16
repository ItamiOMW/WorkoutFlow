package com.itami.workout_flow.app.plugins

import com.itami.workout_flow.app.di.coreModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(coreModule)
    }
}