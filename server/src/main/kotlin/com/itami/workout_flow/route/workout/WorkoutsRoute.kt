package com.itami.workout_flow.route.workout

import com.itami.workout_flow.app.plugins.DEFAULT_RATE_LIMIT
import com.itami.workout_flow.auth.firebase.FIREBASE_AUTH
import com.itami.workout_flow.auth.firebase.FirebasePrincipal
import com.itami.workout_flow.routes.WorkoutsRoute
import com.itami.workout_flow.service.workout.WorkoutService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.plugins.ratelimit.rateLimit
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.workoutsRoute(workoutService: WorkoutService) {
    rateLimit(DEFAULT_RATE_LIMIT) {
        authenticate(FIREBASE_AUTH, optional = true) {
            get<WorkoutsRoute> { workoutsRoute ->
                val firebasePrincipal = call.principal<FirebasePrincipal>()
                val workoutsResponse = workoutService.getWorkouts(
                    userId = firebasePrincipal?.user?.id,
                    workoutsRoute = workoutsRoute,
                )
                call.respond(HttpStatusCode.OK, workoutsResponse)
            }
        }
        authenticate(FIREBASE_AUTH) {
            post<WorkoutsRoute> {
                // Not implemented yet
                call.respond(HttpStatusCode.NotImplemented)
            }
        }
    }
}