package com.example.newsapp.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newsapp.data.api.ApiServiceCorona
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.General
import com.example.newsapp.data.model.Total
import com.example.newsapp.fake.FakeRepository
import com.example.newsapp.fake.FakeRepositoryCorona
import com.example.newsapp.fake.FakeUseCase
import com.example.newsapp.fake.FakeUseCaseCorona
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mock
import org.mockito.Mockito.times
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CoronaViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CoronaViewModel
    private lateinit var fakeUseCase: FakeUseCaseCorona
    private lateinit var fakeRepo: FakeRepositoryCorona

    @Before
    fun setup() {
        fakeRepo = FakeRepositoryCorona()
        fakeUseCase = FakeUseCaseCorona(fakeRepo)
        viewModel = CoronaViewModel(fakeUseCase)
    }

    @Test
    fun fetchRepoList() {
        val total = Total(1, 20, 0, 0)
        fakeRepo.insertArticle(total)
        val allData = viewModel.getTotal()
        MatcherAssert.assertThat(allData.value, `is`(total))
    }
}