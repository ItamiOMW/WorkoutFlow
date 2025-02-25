package com.itami.workout_flow.core.data.local.database

import androidx.room.RoomDatabaseConstructor

// Room will generate actual implementations, so just suppress NO_ACTUAL_FOR_EXPECT
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object WorkoutFlowDbConstructor : RoomDatabaseConstructor<WorkoutFlowDatabase> {

    override fun initialize(): WorkoutFlowDatabase

}