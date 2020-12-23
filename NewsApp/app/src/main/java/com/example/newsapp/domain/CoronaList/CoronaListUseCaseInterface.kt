package com.example.newsapp.domain.CoronaList

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Total

interface CoronaListUseCaseInterface {
        fun getRepoList(): LiveData<Total>
}