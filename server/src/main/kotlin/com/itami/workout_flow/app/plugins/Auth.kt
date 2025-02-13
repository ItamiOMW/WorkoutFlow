package com.itami.workout_flow.app.plugins

import com.itami.workout_flow.auth.firebase.firebase
import com.itami.workout_flow.service.auth.AuthService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import org.koin.ktor.ext.inject

fun Application.configureAuth() {
    val authService: AuthService by inject()
    install(Authentication) {
        firebase {
            validate { firebaseToken ->
                authService.getUserWithFirebase(firebaseToken)
            }
        }
    }
}