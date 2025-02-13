package com.itami.workout_flow.route.auth

import com.itami.workout_flow.app.plugins.AUTH_RATE_LIMIT
import com.itami.workout_flow.data.mapper.toUserResponse
import com.itami.workout_flow.auth.firebase.FIREBASE_AUTH
import com.itami.workout_flow.auth.firebase.FirebasePrincipal
import com.itami.workout_flow.routes.AuthRoute
import com.itami.workout_flow.service.auth.AuthService
import com.itami.workout_flow.utils.AppError
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.plugins.ratelimit.rateLimit
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.authRoute(authService: AuthService) {
    rateLimit(AUTH_RATE_LIMIT) {
        authenticate(FIREBASE_AUTH) {
            get<AuthRoute> {
                val firebasePrincipal = this.call
                    .principal<FirebasePrincipal>()
                    ?: throw AppError.UnauthorizedError()

                call.respond(HttpStatusCode.OK, firebasePrincipal.user.toUserResponse())
            }
        }
    }
}