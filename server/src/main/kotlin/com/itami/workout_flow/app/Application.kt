package com.itami.workout_flow.app

import com.itami.workout_flow.app.plugins.configureKoin
import com.itami.workout_flow.app.plugins.configureMonitoring
import com.itami.workout_flow.app.plugins.configureRateLimit
import com.itami.workout_flow.app.plugins.configureRouting
import com.itami.workout_flow.app.plugins.configureSerialization
import com.itami.workout_flow.app.plugins.configureStatusPages
import com.itami.workout_flow.data.firebase.FirebaseAdmin
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    FirebaseAdmin.init()
    configureKoin()
    configureRouting()
    configureMonitoring()
    configureRateLimit()
    configureSerialization()
    configureStatusPages()
}