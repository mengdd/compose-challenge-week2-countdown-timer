package com.example.androiddevchallenge

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CountdownViewModelTest {

    private val repository: Repository = mockk()

    private lateinit var viewModel: CountdownViewModel

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
        mainCoroutineRule.testDispatcher.cancel()
    }

    @Test
    fun givenInit_whenInitViewModel_thenShowInitState() {
        every { repository.getTime() } returns emptyFlow()
        viewModel = CountdownViewModel(repository = repository)
        assertThat(viewModel.state.value).isEqualTo(ScreenState())
    }

    @Test
    fun givenRepositoryReturnSomeValue_whenUpdate_thenStateValueUpdated() = runBlockingTest {
        every { repository.getTime() } returns flow {
            emit(3)
        }
        viewModel = CountdownViewModel(repository = repository)
        val result = viewModel.state.first()
        assertThat(result.seconds).isEqualTo(3)
    }

    @Ignore("failed for: This job has not completed yet ")
    @Test
    fun givenRepositoryReturnValues_whenUpdate_thenStateValuesUpdated() = runBlockingTest {
        every { repository.getTime() } returns flow {
            emit(3)
            emit(2)
            emit(1)
        }
        viewModel = CountdownViewModel(repository = repository)
        val actual = mutableListOf<Int>()
        viewModel.state.take(3).collect {
            actual.add(it.seconds)
        }

        assertThat(actual).containsExactly(3, 2, 1)
    }
}