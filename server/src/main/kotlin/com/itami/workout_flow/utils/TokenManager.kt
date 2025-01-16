package com.itami.workout_flow.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.itami.workout_flow.data.model.user.User
import java.util.Date

object TokenManager {

    private val secret = System.getenv("JWT_SECRET")
    private val issuer = System.getenv("JWT_ISSUER")
    private val audience = System.getenv("JWT_AUDIENCE")
    private val tokenExpirationTimeMillis = System.getenv("JWT_EXPIRATION_TIME").toLong()

    private val algorithm = Algorithm.HMAC256(secret)

    val tokenVerifier: JWTVerifier = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun generateAuthToken(user: User): String {
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("id", user.id)
            .withExpiresAt(Date(System.currentTimeMillis() + tokenExpirationTimeMillis))
            .sign(algorithm)
    }

}