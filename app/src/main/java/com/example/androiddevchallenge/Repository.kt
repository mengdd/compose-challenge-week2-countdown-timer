package com.example.androiddevchallenge

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor() {
    var count = 60
    fun getTime(): Flow<Int> {
        return flow {
            while (true) {
                emit(count--)
                delay(1000)
            }
        }
    }
}