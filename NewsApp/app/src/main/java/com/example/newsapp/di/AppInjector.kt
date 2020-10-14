package com.example.newsapp.di

import android.content.SharedPreferences
import com.example.newsapp.data.api.ApiClient
import com.example.newsapp.data.repository.RepoListDataStore
import com.example.newsapp.domain.GetRepoListUseCase
import com.example.newsapp.viewmodel.RepoListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val viewModelModule = module {
    viewModel { RepoListViewModel(get()) }
}
val useCaseModule = module {
    single { GetRepoListUseCase(get<RepoListDataStore>()) }
}
val repositoryModule = module {
    single { RepoListDataStore(get()) }
}

val networkModule = module {
    single { ApiClient.create(okHttpClient = get()) }
    single { ApiClient.getOkHttpClient(authInterceptor = get()) }
    single { ApiClient.getAuthInterceptor(sharedPreferences = get()) }
}
val sharedPrefModule = module {
    single {
        androidApplication().getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
    }

    single<SharedPreferences.Editor> {
        androidApplication().getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
            .edit()
    }
}