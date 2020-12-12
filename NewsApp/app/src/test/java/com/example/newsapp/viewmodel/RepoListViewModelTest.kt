package com.example.newsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.data.model.Articles
import com.example.newsapp.fake.FakeRepository
import com.example.newsapp.fake.FakeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.emptyIterable
import org.hamcrest.Matchers.hasItem
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


class RepoListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RepoListViewModel
    private lateinit var fakeUseCase: FakeUseCase
    private lateinit var fakeRepo: FakeRepository

    @Before
    fun setup() {
        fakeRepo = FakeRepository()
        fakeUseCase = FakeUseCase(fakeRepo)
        viewModel = RepoListViewModel(fakeUseCase)
    }

    @Test
    fun fetchRepoList() {
        val article = Articles(1, "", "", "", "", "", "", "")
        fakeRepo.insertArticle(article)
        val allData = viewModel.fetchRepoList()
        MatcherAssert.assertThat(allData.value, hasItem(article))
    }
}