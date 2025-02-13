package com.appsolutely.newsapp.common


import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.format.DateTimeFormatter

fun String.timeElapsed(): String {
    val formatter = DateTimeFormatter.ISO_INSTANT
    val pastTime = Instant.from(formatter.parse(this))
    val currentTime = Instant.now()

    val diff = ChronoUnit.SECONDS.between(pastTime, currentTime)

    return when {
        diff < 60 -> "${diff}S"
        diff < 3600 -> "${diff / 60}M"
        diff < 86400 -> "${diff / 3600}H"
        diff < 604800 -> "${diff / 86400}D"
        else -> "${diff / 604800} W"
    }
}

