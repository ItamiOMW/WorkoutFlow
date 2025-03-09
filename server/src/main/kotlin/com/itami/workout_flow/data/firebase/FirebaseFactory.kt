package com.itami.workout_flow.data.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.InputStream

object FirebaseFactory {

    private val firebaseAdminSdk: InputStream = System.getenv("FIREBASE_ADMIN_SDK").byteInputStream()

    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(firebaseAdminSdk))
        .build()

    fun init() {
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }

}