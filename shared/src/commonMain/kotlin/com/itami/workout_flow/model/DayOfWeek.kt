package com.itami.workout_flow.model;

enum class DayOfWeek(val dayNumber: Int) {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    companion object {
        fun fromDayNumber(dayNumber: Int): DayOfWeek {
            return entries.find { it.dayNumber == dayNumber } ?: MONDAY
        }
    }
}