package dev.quisto.core.util

object TimeParser {

    fun Int.parseTime(): String {
        return when {
            this < 1 -> "1 Minute"
            this < 60 -> "$this Minutes"
            this >= 60 -> "${this/60}:${this % 60} Hours(s) "
            else -> {
                "Unknown Time"
            }
        }
    }
}