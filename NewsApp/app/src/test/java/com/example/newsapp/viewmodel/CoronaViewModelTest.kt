package com.example.newsapp.viewmodel
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.newsapp.fake.FakeServiceClass
//import com.example.newsapp.getOrAwaitValue
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runBlockingTest
//import org.hamcrest.CoreMatchers.not
//import org.hamcrest.CoreMatchers.nullValue
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//class CoronaViewModelTest {
////    private lateinit var myRemoteDataSource: FakeServiceClass
////    private lateinit var myViewModel: CoronaViewModel
//////    @get:Rule
//////    var instantExecutorRule = InstantTaskExecutorRule()
////
////    @Before
////    fun createRepository() {
////        myRemoteDataSource = FakeServiceClass()
////        myViewModel = CoronaViewModel(myRemoteDataSource)
////    }
////
////    @Test
////    fun getTotal_check() = runBlockingTest {
////        val value = myViewModel.liveData.getOrAwaitValue()
////        assertThat(value, not(nullValue()))
////    }
//}