package com.example.skeletonproject.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * helper function to add "https:" in the icon which is a url that we get in the condition object
 *
 * ex: //cdn.weatherapi.com/weather/64x64/day/113.png
 * result: https://cdn.weatherapi.com/weather/64x64/day/113.png
 */
fun getIconUrl(url: String?): String? {
    return if (url != null) "https:$url" else null
}

fun getTimeFormatted(timeEpoch: String?): String? {
    return if (timeEpoch != null) {
        val mainDateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm", Locale.ENGLISH)
        val unixDate = mainDateFormat.parse(timeEpoch)
        val dateFormat = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
        if (unixDate != null) {
            dateFormat.format(unixDate)
        } else {
            null
        }
    } else {
        null
    }
}

fun getDateFormatted(timeEpoch: String?): String? {
    return if (timeEpoch != null) {
        val mainDateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm", Locale.ENGLISH)
        val unixDate = mainDateFormat.parse(timeEpoch)
        val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH)
        if (unixDate != null) {
            dateFormat.format(unixDate)
        } else {
            null
        }
    } else {
        null
    }
}

fun getNameOfDay(dateEpoch: String?): String? {
    return if (dateEpoch != null) {
        val mainDateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
        val unixDate = mainDateFormat.parse(dateEpoch)
        val dateFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
        if (unixDate != null) {
            dateFormat.format(unixDate)
        } else {
            null
        }
    } else {
        null
    }
}