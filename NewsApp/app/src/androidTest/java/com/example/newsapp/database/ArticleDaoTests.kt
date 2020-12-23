package com.example.newsapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.newsapp.data.model.Articles
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ArticleDaoTests {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ArticleDatabase
    private lateinit var dao: ArticleDao


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.articleDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertArticle() = runBlockingTest {
        val articleItem = Articles(1, "", "", "", "", "", "", "")
        dao.insert(articleItem)

        val allArticles = dao.getAll()
        MatcherAssert.assertThat(allArticles, CoreMatchers.hasItem(articleItem))
    }

    @Test
    fun insertAllArticle() = runBlockingTest {
        val articleItem = Articles(1, "", "hi", "", "", "", "", "")
        val articleItem2 = Articles(2, "", "hello", "", "", "", "", "")
        val list = ArrayList<Articles>()
        list.add(articleItem)
        list.add(articleItem2)
        dao.insertAll(list)

        val allArticles = dao.getAll()
        MatcherAssert.assertThat(allArticles, CoreMatchers.hasItems(articleItem, articleItem2))
    }
}