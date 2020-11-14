package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import com.example.android.architecture.blueprints.todoapp.Event
import org.hamcrest.CoreMatchers.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setupViewModel() {
        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {
//
//        // Given a fresh TasksViewModel
//        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
//
//        // Create observer - no need for it to do anything!
//
//        val observer = Observer<Event<Unit>> {}
//        try {
//
//            // Observe the LiveData forever
//            tasksViewModel.newTaskEvent.observeForever(observer)
//
//            // When adding a new task
//            tasksViewModel.addNewTask()
//
//            // Then the new task event is triggered
//            val value = tasksViewModel.newTaskEvent.value
//
//
//            assertThat(value?.getContentIfNotHandled(), (not(nullValue())))
//
//        } finally {
//            // Whatever happens, don't forget to remove the observer!
//            tasksViewModel.newTaskEvent.removeObserver(observer)
//        }
        // Given a fresh ViewModel
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When adding a new task
        tasksViewModel.addNewTask()

        // Then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }
    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {

        // Given a fresh ViewModel
        //  val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
// Subject under test

        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))

    }
}
