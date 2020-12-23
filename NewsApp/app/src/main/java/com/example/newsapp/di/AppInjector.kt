package com.example.newsapp.di

import android.content.SharedPreferences
import com.example.newsapp.data.api.ApiClient
import com.example.newsapp.data.repository.CoronaList.CoronaListDataStore
import com.example.newsapp.data.repository.GeneralList.GeneralListDataStore
import com.example.newsapp.data.repository.RepoList.RepoListDataStore
import com.example.newsapp.domain.CoronaList.CoronaListUseCase
import com.example.newsapp.domain.GeneralList.GetGeneralListUseCase
import com.example.newsapp.domain.RepoList.GetRepoListUseCase
import com.example.newsapp.viewmodel.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val viewModelModule = module {
    viewModel { CoronaViewModel(get<CoronaListUseCase>()) }
    viewModel { ThirdViewModel(get()) }
    viewModel { SourcesListViewModel(get(), get()) }
    viewModel { LikesViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { RepoListViewModel(get<GetRepoListUseCase>()) }
    viewModel { GeneralListViewModel(get()) }
}

val useCaseModule = module {
    single { GetRepoListUseCase(get<RepoListDataStore>()) }
    single { GetGeneralListUseCase(get<GeneralListDataStore>()) }
    single { CoronaListUseCase(get<CoronaListDataStore>()) }

}
val repositoryModule = module {
    single { RepoListDataStore(get(), androidApplication()) }
    single { CoronaListDataStore(get(), androidApplication()) }
    single { GeneralListDataStore(get(), androidApplication()) }
}

val networkModule = module {
    single { ApiClient.create(okHttpClient = get()) }
    single { ApiClient.createCorona(okHttpClient = get()) }
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