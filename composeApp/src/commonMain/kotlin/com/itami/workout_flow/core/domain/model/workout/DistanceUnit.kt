package com.itami.workout_flow.core.domain.model.workout;

enum class DistanceUnit(private val metersPerUnit: Float) {
    KILOMETERS(metersPerUnit = 1000.0f),
    MILES(metersPerUnit = 1609.34f);

    fun toMeters(distance: Float): Float {
        return when (this) {
            KILOMETERS -> {
                kilometersToMeters(distance)
            }

            MILES -> {
                milesToMeters(distance)
            }
        }
    }

    fun fromMeters(meters: Float): Float {
        return when (this) {
            KILOMETERS -> {
                metersToKilometers(meters)
            }

            MILES -> {
                metersToMiles(meters)
            }
        }
    }

    private fun metersToKilometers(meters: Float): Float {
        return meters / KILOMETERS.metersPerUnit
    }

    private fun kilometersToMeters(kilometers: Float): Float {
        return kilometers * KILOMETERS.metersPerUnit
    }

    private fun metersToMiles(meters: Float): Float {
        return meters / MILES.metersPerUnit
    }

    private fun milesToMeters(miles: Float): Float {
        return miles * MILES.metersPerUnit
    }

}