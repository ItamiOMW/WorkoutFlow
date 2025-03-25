package com.itami.workout_flow.app.plugins

import com.itami.workout_flow.route.auth.authRoute
import com.itami.workout_flow.route.workout.workoutsRoute
import com.itami.workout_flow.routes.HomeRoute
import com.itami.workout_flow.service.AuthService
import com.itami.workout_flow.service.WorkoutService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val authService: AuthService by inject()
    val workoutService: WorkoutService by inject()
    install(Resources)
    routing {
        get<HomeRoute> {
            call.respondText("Welcome to WorkoutFlow!")
        }
        authRoute(authService = authService)
        workoutsRoute(workoutService = workoutService)
    }
}