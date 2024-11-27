package com.kelly.core.common.utils

import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

inline fun <reified T> String?.fromJson(): T? {
    return Gson().fromJson(this, T::class.java)
}

fun <T> T.toJson(): String {
    return Gson().toJson(this)
}

fun getIcon(iconCode: String): String {
    return "https://openweathermap.org/img/wn/${iconCode}@2x.png"
}

fun String.parseDate(pattern: String): Date {
    return try {
        SimpleDateFormat(pattern, Locale.getDefault()).parse(this) ?: Date()
    } catch (t: Throwable) {
        t.printStackTrace()
        Date()
    }
}

fun <T> T.formatDateWithPattern(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)