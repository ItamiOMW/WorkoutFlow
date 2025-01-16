package com.itami.workout_flow.utils

import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal

fun ApplicationCall.userId(): Long? = this.principal<JWTPrincipal>()
    ?.getClaim("id", Long::class)