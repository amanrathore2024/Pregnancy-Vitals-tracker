package com.vitalstraker.presentation.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getCurrentFormattedDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern(
        "EEE, dd MMM yyyy hh:mm a",
        Locale.ENGLISH
    )

    return LocalDateTime.now().format(formatter).uppercase()
}
