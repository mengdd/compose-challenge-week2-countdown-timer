package com.example.androiddevchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ScreenState(val seconds: Int = 0)

@ExperimentalCoroutinesApi
@HiltViewModel
class CountdownViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    init {
        Log.i("ddmeng", "init ViewModel")
    }

    val state: StateFlow<ScreenState> =
        repository.getTime().mapLatest { number ->
            ScreenState(seconds = number)
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                ScreenState(seconds = 0)
            )

    fun startCountdown() {
        repository.count = 60
    }
}