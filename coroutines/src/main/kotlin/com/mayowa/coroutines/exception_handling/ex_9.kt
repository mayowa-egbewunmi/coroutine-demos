package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * In the previous example, the async method didn't throw exception until the await method is called.
 *
 * But in the example below, the exception is thrown. Why?
 *
 * Rethrowing and handling of an exception is delegated to the Parent coroutine
 */
fun main() {
    val rootScope = CoroutineScope(Job())
    rootScope.launch {
        async {
            throw Exception()
        }
    }

    //Sleep the main thread to prevent function exit
    Thread.sleep(100)
}