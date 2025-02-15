package com.itami.workout_flow.auth.firebase

import com.google.firebase.auth.FirebaseToken
import com.itami.workout_flow.data.model.user.User

data class FirebasePrincipal(
    val firebaseToken: FirebaseToken,
    val user: User
)
