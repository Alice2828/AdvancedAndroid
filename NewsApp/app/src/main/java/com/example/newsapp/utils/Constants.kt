package com.example.newsapp.utils

import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"

        //  const val Api_key = "b0fe0f0da9634ed890cefa676654c7d8"
        // const val Api_key = "ba3124758cef43b1b740a42c78e7641c"37f02cdeb8f24d75a86e4ddd90691137
        const val Api_key = "37f02cdeb8f24d75a86e4ddd90691137"

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
}