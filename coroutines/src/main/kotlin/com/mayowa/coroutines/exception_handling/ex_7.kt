package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*
import kotlin.Exception

/**
 * We also need to be careful of resource leakage when using try catch.
 *
 * Avoid using a generic try catch exception
 *
 * Solution: Rethrow CancellationException
 *
 */

fun main() {
    val rootScope = CoroutineScope(Job())

    rootScope.launch {
        val result1 = launch {
            try {
                networkRequest1()
            } catch (exception: Exception) {
                println("Handled $exception")
                if (exception is CancellationException) {
                    println("Cancellation exception handled")
                    throw exception
                }
            }
            println("Coroutine for result1 is still running")
        }

        val result2 = launch {
            networkRequest1()
            delay(200)
            println("coroutine 2")
        }
        delay(200)
        result1.cancel()
    }

    //Sleep the main thread to prevent function exit
    Thread.sleep(1000)
}

private suspend fun networkRequest1(): String {
    return withContext(Dispatchers.IO) {
        // Perform some operations
        //throw Exception()
        delay(400)
        return@withContext "Result 1"
    }
}
