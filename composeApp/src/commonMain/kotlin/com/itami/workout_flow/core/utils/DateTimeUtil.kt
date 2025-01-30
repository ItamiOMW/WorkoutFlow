package com.itami.workout_flow.core.utils
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

object DateTimeUtil {

    fun getCurrentDate(): LocalDate {
        val localDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime.date
    }

    fun getCurrentWeekDates(): List<LocalDate> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val dayOfWeek = today.dayOfWeek.isoDayNumber // 1 for Monday, 7 for Sunday
        val startOfWeek = today.minus(dayOfWeek, DateTimeUnit.DAY)
        return (1..7).map { startOfWeek.plus(it, DateTimeUnit.DAY) }
    }

}