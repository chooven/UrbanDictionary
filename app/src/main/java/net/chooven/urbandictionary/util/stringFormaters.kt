package net.chooven.urbandictionary.util

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


object StringFormatter {

    fun convertTimestampToDayAndHourFormat(date: Date?): String{
        if(date == null){
            return ""
        }
        val hourFormat = "hh:mm a"
        val formatter = SimpleDateFormat (hourFormat, Locale.ENGLISH)
        return formatter.format(date)
    }
    fun convertTimestampToDateFormat(date: Date?): String{
        if(date == null){
            return ""
        }
        return try {
            val dateFormat = "MM/dd/yy"
            val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            formatter.format(date)
        } catch (e: Exception){
            Timber.e(e,"Error formatting Date string: $date")
            "N/A"
        }
    }

}
