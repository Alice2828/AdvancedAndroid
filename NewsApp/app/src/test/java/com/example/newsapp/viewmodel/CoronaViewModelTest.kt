package com.example.newsapp.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newsapp.data.api.ApiServiceCorona
import com.example.newsapp.data.model.General
import com.example.newsapp.data.model.Total
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.MatcherAssert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.`is`
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CoronaViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private lateinit var viewModel: CoronaViewModel

    @Mock
    lateinit var observer: Observer<CoronaViewModel.State>

//    @Mock
//    lateinit var mockService: ApiServiceCorona

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun fetchRepoList() = testScope.runBlockingTest {
        val total = General(Total(0, 0, 0, 0))

        val mockService = mock<ApiServiceCorona> {
            onBlocking { getTotal() } doReturn Response.success(total)
        }
     //   observer = Observer<CoronaViewModel.State>()
        // make the github api to return mock data
//        Mockito.`when`(mockService.getTotal())
//            .thenReturn(Observzable.just(Response.success(total)))

        viewModel = CoronaViewModel(mockService)
        viewModel.liveData.observeForever(observer)
        viewModel.getTotal()
//        MatcherAssert.assertThat(
//            viewModel.liveData.value, `is`(Total(0, 0, 0, 0))
//        )
        assert(viewModel.liveData.value == CoronaViewModel.State.Result(total))
    }
}