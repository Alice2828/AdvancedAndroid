package com.example.newsapp.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.example.newsapp.data.api.ApiClient
import com.example.newsapp.data.repository.GeneralListDataStore
import com.example.newsapp.data.repository.RepoListDataStore
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.database.ArticleDatabase
import com.example.newsapp.database.LikesDao
import com.example.newsapp.database.LikesDatabase
import com.example.newsapp.domain.GeneralList.GetGeneralListUseCase
import com.example.newsapp.domain.RepoList.GetRepoListUseCase
import com.example.newsapp.viewmodel.GeneralListViewModel
import com.example.newsapp.viewmodel.LikesViewModel
import com.example.newsapp.viewmodel.RepoListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val viewModelModule = module {
  //  viewModel { LikesViewModel(get()) }
    viewModel { RepoListViewModel(get(), get()) }
    viewModel { GeneralListViewModel(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): ArticleDatabase {
        return Room.databaseBuilder(
            application,
            ArticleDatabase::class.java,
            "artic_database.db"
        ).allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: ArticleDatabase): ArticleDao {
        return database.articleDao()
    }

//    fun provideLikesDatabase(application: Application): LikesDatabase {
//        return Room.databaseBuilder(
//            application,
//            LikesDatabase::class.java,
//            "likes_database.db"
//        ).allowMainThreadQueries()
//            .build()
//    }
//
//    fun provideLikesDao(database: LikesDatabase): LikesDao {
//        return database.likesDao()
//    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
//    single { provideLikesDatabase(androidApplication()) }
//    single { provideLikesDao(get()) }
}

val useCaseModule = module {
    single { GetRepoListUseCase(get<RepoListDataStore>()) }
    single { GetGeneralListUseCase(get<GeneralListDataStore>()) }
}
val repositoryModule = module {
    single { RepoListDataStore(get(), get()) }
    single { GeneralListDataStore(get(), get()) }
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