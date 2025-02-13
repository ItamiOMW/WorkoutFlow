package com.itami.workout_flow.auth.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.itami.workout_flow.utils.AppError
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.AuthenticationContext
import io.ktor.server.auth.AuthenticationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseAuthProvider(config: FirebaseConfig) : AuthenticationProvider(config) {
    val authHeader: (ApplicationCall) -> HttpAuthHeader? = config.authHeader
    private val authFunction = config.firebaseAuthenticationFunction

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        try {
            val header = authHeader(context.call) ?: throw AppError.UnauthorizedError("Invalid token")
            val token = verifyFirebaseIdToken(authHeader = header) ?: throw AppError.UnauthorizedError("Invalid token")
            val principal = authFunction(context.call, token) ?: throw AppError.UnauthorizedError("Invalid token")
            context.principal(principal)
        } catch (e: Exception) {
            e.printStackTrace()
            throw AppError.UnauthorizedError()
        }
    }

    private suspend fun verifyFirebaseIdToken(
        authHeader: HttpAuthHeader,
    ): FirebaseToken? {
        return if (authHeader.authScheme == "Bearer" && authHeader is HttpAuthHeader.Single) {
            withContext(Dispatchers.IO) {
                FirebaseAuth.getInstance().verifyIdToken(authHeader.blob)
            }
        } else {
            null
        }
    }
}