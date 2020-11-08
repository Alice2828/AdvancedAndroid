package com.example.pagingapp.datasource


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.pagingapp.model.User


class UserDataSourceFactory: DataSource.Factory<Int,User>(){

    val userLiveDataSource = MutableLiveData<UserDataSource>()

    override fun create(): DataSource<Int, User> {
        val userDataSource =
            UserDataSource()
        userLiveDataSource.postValue(userDataSource)

        return userDataSource

    }
}