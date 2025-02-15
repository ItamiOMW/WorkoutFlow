package com.itami.workout_flow.app

import com.itami.workout_flow.app.plugins.configureAuth
import com.itami.workout_flow.app.plugins.configureKoin
import com.itami.workout_flow.app.plugins.configureMonitoring
import com.itami.workout_flow.app.plugins.configureRateLimit
import com.itami.workout_flow.app.plugins.configureRouting
import com.itami.workout_flow.app.plugins.configureSerialization
import com.itami.workout_flow.app.plugins.configureStatusPages
import com.itami.workout_flow.data.database.DatabaseFactory
import com.itami.workout_flow.data.firebase.FirebaseFactory
import com.itami.workout_flow.data.repository.user.UserRepository
import io.ktor.server.application.Application
import org.koin.ktor.ext.inject

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    DatabaseFactory.init()
    FirebaseFactory.init()
    configureKoin()
    configureAuth()
    configureRateLimit()
    configureRouting()
    configureMonitoring()
    configureSerialization()
    configureStatusPages()
}