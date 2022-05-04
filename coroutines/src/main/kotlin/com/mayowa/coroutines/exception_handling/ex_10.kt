package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Using coroutineScope { } for exception handling
 *
 * coroutineScope{} rethrows uncaught exception of its child coroutine
 */

fun main() {
    val rootScope = CoroutineScope(Job())
    rootScope.launch {
        try {
            coroutineScope {
                launch {
                    throw Exception()
                }
            }
        } catch (exception: Exception) {
            println("Handle $exception")
        }
    }

    //Sleep the main thread to prevent function exit
    Thread.sleep(1000)
}