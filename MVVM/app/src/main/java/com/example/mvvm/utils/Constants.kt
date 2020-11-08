package com.example.mvvm.utils

import androidx.paging.PagedList

//class Constants {
//    companion object {
//        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
//        const val REQUEST_TIMEOUT_DURATION = 10
//        const val DEBUG = true
//    }
//}
class Constants {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val Api_key = "b0fe0f0da9634ed890cefa676654c7d8"
        const val REQUEST_TIMEOUT_DURATION = 10
        const val DEBUG = true
    }
}

fun pagedListConfig() = PagedList.Config.Builder()
    .setInitialLoadSizeHint(5)
    .setEnablePlaceholders(false)
    .setPageSize(5 * 2)
    .build()