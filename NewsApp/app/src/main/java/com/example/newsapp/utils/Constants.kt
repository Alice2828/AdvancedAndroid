package com.example.newsapp.utils

import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val Api_key = "b0fe0f0da9634ed890cefa676654c7d8"

        // const val Api_key = "ba3124758cef43b1b740a42c78e7641c"
        const val REQUEST_TIMEOUT_DURATION = 10
        const val DEBUG = true
    }
}

class Utils {
    fun DateToTimeFormat(oldstringDate: String?): String? {
        val p = PrettyTime(Locale("us"))
        var isTime: String? = null
        try {
            val sdf = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.ENGLISH
            )
            val date: Date = sdf.parse(oldstringDate)
            isTime = p.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return isTime
    }

    fun DateFormat(oldstringDate: String?): String? {
        val newDate: String?
        val dateFormat = SimpleDateFormat("E, d MMM yyyy", Locale("us"))
        newDate = try {
            val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate)
            dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            oldstringDate
        }
        return newDate
    }

}