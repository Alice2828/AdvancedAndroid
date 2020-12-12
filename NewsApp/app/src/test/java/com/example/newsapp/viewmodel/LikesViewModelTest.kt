package com.example.newsapp.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Likes
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.database.ArticleDatabase
import com.example.newsapp.database.LikesDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class LikesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: LikesViewModel
    private lateinit var dao: LikesDao
    private lateinit var daoArt: ArticleDao
    private lateinit var database: ArticleDatabase


    @Before
    fun setup() {
        viewModel = LikesViewModel(ApplicationProvider.getApplicationContext())
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.likesDao()
        daoArt = database.articleDao()
        daoArt.insert(Articles(1, "", "hello", "", "", "", "", ""))
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun fetchRepoList() = runBlockingTest {
        val article = Likes(1, "hello", "hi")
        dao.insert(article)
        val likedArticle = dao.getByTitle("hello", "hi")
        val articles = dao.getAll("hi")
        MatcherAssert.assertThat(articles, CoreMatchers.hasItem(likedArticle))
    }

}