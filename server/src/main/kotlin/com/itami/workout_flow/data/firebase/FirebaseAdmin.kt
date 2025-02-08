package com.itami.workout_flow.data.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.InputStream

object FirebaseAdmin {

    private val firebaseAdminSdk: InputStream = System.getenv("FIREBASE_ADMIN_KEY").byteInputStream()

    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(firebaseAdminSdk))
        .build()

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)

}