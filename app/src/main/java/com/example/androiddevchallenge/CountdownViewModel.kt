package com.example.androiddevchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class CountdownViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val seconds = repository.getTime()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 60)

    fun startCountdown() {

    }
}