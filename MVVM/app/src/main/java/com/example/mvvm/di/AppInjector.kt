package com.example.mvvm.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.example.mvvm.data.api.ApiClient
import com.example.mvvm.data.repository.RepoListDataStore
import com.example.mvvm.database.PostDao
import com.example.mvvm.database.PostsDatabase
import com.example.mvvm.domain.GetRepoListUseCase
import com.example.mvvm.viewmodel.RepoListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val viewModelModule = module {
    viewModel { RepoListViewModel(get()) }
}
val useCaseModule = module {
    single { GetRepoListUseCase(get<RepoListDataStore>()) }
}
val repositoryModule = module {
    single { RepoListDataStore(get(), get()) }
}
val databaseModule = module {

    fun provideDatabase(application: Application): PostsDatabase {
        return Room.databaseBuilder(
            application,
            PostsDatabase::class.java,
            "posts_database.db"
        ).allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: PostsDatabase): PostDao {
        return  database.postDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}
val networkModule = module {
    single { ApiClient.create(okHttpClient = get(), androidContext()) }
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