package com.itami.workout_flow.auth.firebase

import io.ktor.server.auth.AuthenticationConfig

const val FIREBASE_AUTH = "FIREBASE_AUTH"

fun AuthenticationConfig.firebase(
    name: String? = FIREBASE_AUTH,
    configure: FirebaseConfig.() -> Unit
) {
    val provider = FirebaseAuthProvider(FirebaseConfig(name).apply(configure))
    register(provider)
}