package com.example.androiddevchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScreenState(val seconds: Int = TOTAL) {
    val progress: Float
        get() = seconds.toFloat() / TOTAL.toFloat()
}

const val TOTAL = 60

@ExperimentalCoroutinesApi
@HiltViewModel
class CountdownViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()
    private var job: Job? = null
    fun startCountdown() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getTime(TOTAL).mapLatest { number ->
                ScreenState(seconds = number)
            }.collect {
                _state.value = it
            }
        }

    }
}