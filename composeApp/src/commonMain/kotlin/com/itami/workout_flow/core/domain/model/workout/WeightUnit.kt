package com.itami.workout_flow.core.domain.model.workout;

enum class WeightUnit(private val gramsPerUnit: Float) {
    KILOGRAMS(gramsPerUnit = 1000.0f),
    POUNDS(gramsPerUnit = 453.592f);

    fun toGrams(weight: Float): Float {
        return when (this) {
            POUNDS -> {
                poundsToGrams(weight)
            }

            KILOGRAMS -> {
                kilogramsToGrams(weight)
            }
        }
    }

    fun fromGrams(weightGrams: Float): Float {
        return when (this) {
            POUNDS -> {
                gramsToPounds(weightGrams)
            }

            KILOGRAMS -> {
                gramsToKilograms(weightGrams)
            }
        }
    }

    private fun gramsToPounds(grams: Float): Float {
        return grams / POUNDS.gramsPerUnit
    }

    private fun poundsToGrams(pounds: Float): Float {
        return pounds * POUNDS.gramsPerUnit
    }

    private fun gramsToKilograms(grams: Float): Float {
        return grams / KILOGRAMS.gramsPerUnit
    }

    private fun kilogramsToGrams(kilograms: Float): Float {
        return kilograms * KILOGRAMS.gramsPerUnit
    }

}