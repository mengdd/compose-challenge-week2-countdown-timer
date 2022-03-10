package com.example.androiddevchallenge

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor() {
    private var started = false
    fun getTime(startTime: Int): Flow<Int> {
        var count = startTime
        started = true
        return flow {
            while (started) {
                delay(1000)
                emit(count--)
                if (count < 0) {
                    started = false
                }
            }
        }
    }
}