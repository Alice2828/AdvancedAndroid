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
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class DetailViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: DetailViewModel
    private lateinit var dao: LikesDao
    private lateinit var daoArt: ArticleDao
    private lateinit var database: ArticleDatabase
    private lateinit var likedArticle: Articles
    private lateinit var newLikedArticle: Articles
    private lateinit var emailName: String


    @Before
    fun setup() {
        viewModel = DetailViewModel(ApplicationProvider.getApplicationContext())
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).allowMainThreadQueries().build()
        emailName = "hi"
        dao = database.likesDao()
        daoArt = database.articleDao()
        likedArticle = Articles(1, "", "hello", "", "", "", "", "")
        newLikedArticle = Articles(2, "", "newTitle", "", "", "", "", "")
        daoArt.insert(likedArticle)
        daoArt.insert(newLikedArticle)
        dao.insert(Likes(1, "hello", emailName))
    }

    @After
    fun teardown() {
        database.close()
        stopKoin()
    }

    @Test
    fun hasLike() = runBlockingTest {
        val list = dao.getAll(emailName)
        MatcherAssert.assertThat(list, CoreMatchers.hasItem(likedArticle))
    }

    @Test
    fun likeMovie() = runBlockingTest {
        val index = dao.queryLastInsert().plus(1)
        val newLike = Likes(
            index,
            "newTitle",
            emailName
        )
        dao.insert(newLike)
        val list = dao.getAll(emailName)
        MatcherAssert.assertThat(list, CoreMatchers.hasItem(newLikedArticle))

    }

    @Test
    fun unlikeMovie() = runBlockingTest {
        val newLike = Likes(
            2,
            "newTitle",
            emailName
        )
        dao.insert(newLike)
        dao.remove(
            "newTitle",
            emailName
        )
        val list = dao.getAll(emailName)
        MatcherAssert.assertThat(list, not(CoreMatchers.hasItem(newLikedArticle)))
    }
}