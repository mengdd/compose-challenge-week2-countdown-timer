package com.example.androiddevchallenge

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
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

    @Test
    fun givenRepositoryReturnValues_whenUpdate_thenStateValuesUpdated() = runBlockingTest {
        every { repository.getTime() } returns flowOf(3, 2, 1)
        viewModel = CountdownViewModel(repository = repository)
        viewModel.state.test {


            //TODO Hot Flows: https://github.com/cashapp/turbine
            //It's important to call test (and therefore have an active collector) on a flow before emissions to a flow are made. For example:
            assertThat(expectItem()).isEqualTo(ScreenState(seconds = 1))
//                assertThat(expectItem().seconds).isEqualTo(2)
//                assertThat(expectItem().seconds).isEqualTo(1)
            cancelAndIgnoreRemainingEvents()
        }
    }
}