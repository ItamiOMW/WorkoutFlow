package com.itami.workout_flow.routes

import com.itami.workout_flow.dto.request.FirebaseAuthRequest
import com.itami.workout_flow.utils.RESTAPI_VERSION
import io.ktor.resources.Resource

@Resource("$RESTAPI_VERSION/auth")
object AuthRoute {
    @Resource("firebase")
    data class Firebase(val parent: AuthRoute, val firebaseAuthRequest: FirebaseAuthRequest)
}