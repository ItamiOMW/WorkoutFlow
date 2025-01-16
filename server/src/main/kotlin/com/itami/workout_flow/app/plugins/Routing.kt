package com.itami.workout_flow.app.plugins

import com.itami.workout_flow.routes.HomeRoute
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    install(io.ktor.server.resources.Resources)
    routing {
        get<HomeRoute> {
            call.respondText("Welcome to WorkoutFlow!")
        }
    }
}