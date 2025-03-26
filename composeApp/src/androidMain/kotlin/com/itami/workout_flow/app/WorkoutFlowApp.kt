package com.itami.workout_flow.app

import android.app.Application
import com.itami.workout_flow.app.di.initKoin
import org.koin.android.ext.koin.androidContext

class WorkoutFlowApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@WorkoutFlowApp)
        }
    }
}