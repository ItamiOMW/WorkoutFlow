package com.itami.workout_flow.route.exercise

import com.itami.workout_flow.app.plugins.DEFAULT_RATE_LIMIT
import com.itami.workout_flow.auth.firebase.FIREBASE_AUTH
import com.itami.workout_flow.auth.firebase.FirebasePrincipal
import com.itami.workout_flow.routes.ExercisesRoute
import com.itami.workout_flow.service.ExerciseService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.plugins.ratelimit.rateLimit
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.exercisesRoute(exerciseService: ExerciseService) {
    rateLimit(DEFAULT_RATE_LIMIT) {
        authenticate(FIREBASE_AUTH, optional = true) {
            get<ExercisesRoute> { exercisesRoute ->
                val firebasePrincipal = call.principal<FirebasePrincipal>()
                val exercisesResponse = exerciseService.getExercises(
                    userId = firebasePrincipal?.user?.id,
                    page = exercisesRoute.page,
                    pageSize = exercisesRoute.pageSize,
                    query = exercisesRoute.query,
                    muscles = exercisesRoute.muscles,
                    equipments = exercisesRoute.equipments,
                    exerciseTypes = exercisesRoute.exerciseTypes
                )
                call.respond(HttpStatusCode.OK, exercisesResponse)
            }
        }
    }
}