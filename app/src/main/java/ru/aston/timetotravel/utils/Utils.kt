package ru.aston.timetotravel.utils

import java.text.ParseException
import java.text.SimpleDateFormat

object Utils {
    fun getFormattedDate(unformatted: String?): String {
        val formatIn = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatOut = SimpleDateFormat("MMM, dd")
        var formatted = ""
        try {
            formatted = formatOut.format(formatIn.parse(unformatted))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formatted
    }
}