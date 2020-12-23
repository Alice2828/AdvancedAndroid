package com.example.newsapp.data.repository.CoronaList

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.newsapp.data.api.ApiServiceCorona
import com.example.newsapp.data.model.Total
import com.example.newsapp.domain.CoronaList.CoronaListRepository

class CoronaListDataStore(apiService: ApiServiceCorona, context: Context) : CoronaListRepository,
    BaseDataStoreCorona(apiService, context) {
    override fun loadData(): LiveData<Total> {
        return fetchData {
            service.getTotal()
        }
    }
}