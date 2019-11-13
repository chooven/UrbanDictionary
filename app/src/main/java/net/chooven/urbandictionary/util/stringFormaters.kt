package net.chooven.urbandictionary.util

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
        val dateFormat = "MM/dd/YY"
        val formatter = SimpleDateFormat (dateFormat, Locale.ENGLISH)
        return formatter.format(date)
    }

}
