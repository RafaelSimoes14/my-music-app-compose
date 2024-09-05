package com.example.mymusicappcompose.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.timeToString(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}