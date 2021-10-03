package ru.sber.datetime

import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*

// 1.
fun getZonesWithNonDivisibleByHourOffset(): Set<String> {
    return ZoneId.getAvailableZoneIds()
        .filter {
            ZonedDateTime.now(ZoneId.of(it)).minute != ZonedDateTime.now(ZoneId.of("UTC")).minute
        }
        .toSet()
}

// 2.
fun getLastInMonthDayWeekList(year: Int): List<String> {
    return Month.values().map { month ->
        LocalDateTime
            .of(year, month, 1, 0, 0)
            .with(TemporalAdjusters.lastDayOfMonth()).dayOfWeek.name
    }.toList()
}

// 3.
fun getNumberOfFridayThirteensInYear(year: Int): Int {
    var cntFriday = 0
    var list =
        Month.values().map { month ->
            LocalDateTime
                .of(year, month, 13, 1, 0)
                .dayOfWeek.name}
    for(i in list){
        if (i.equals("FRIDAY")) cntFriday++
    }
    return cntFriday
}

// 4.
fun getFormattedDateTime(dateTime: LocalDateTime): String {
    return DateTimeFormatter.ofPattern("dd MMM yyy, HH:mm", Locale.US).format(dateTime)
}