package com.example.newsapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Likes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LikesDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ArticleDatabase
    private lateinit var dao: LikesDao
    private lateinit var daoArt: ArticleDao


    @Before
    fun setup() {
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
    fun insertLike() = runBlockingTest {
        val likeItem = Likes(1, "hello", "Hi")
        dao.insert(likeItem)
        val articlesAllLiked = dao.getAllLikes("Hi")
        MatcherAssert.assertThat(articlesAllLiked, CoreMatchers.hasItem(likeItem))
    }

    @Test
    fun removeLike() = runBlockingTest {
        val likeItem = Likes(1, "hello", "Hi")
        dao.insert(likeItem)
        dao.remove("hello", "Hi")

        val articlesAllLiked = dao.getAllLikes("Hi")
        MatcherAssert.assertThat(articlesAllLiked, not(CoreMatchers.hasItem(likeItem)))
    }

    @Test
    fun queryLastRight() = runBlockingTest {
        val likeItem = Likes(1, "hello", "Hi")
        val likeItem2 = Likes(2, "hello", "Hi")

        dao.insert(likeItem)
        dao.insert(likeItem2)

        val last = dao.queryLastInsert()
        assertThat(2, CoreMatchers.equalTo(last))
    }

    @Test
    fun queryLastWrong() = runBlockingTest {
        val likeItem = Likes(1, "hello", "Hi")
        val likeItem2 = Likes(2, "hello", "Hi")

        dao.insert(likeItem)
        dao.insert(likeItem2)

        val last = dao.queryLastInsert()
        assertThat(4, CoreMatchers.equalTo(last))
    }
}