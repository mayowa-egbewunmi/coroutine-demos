package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*
import java.lang.Exception

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
                if (exception is CancellationException){
                    println("Cancellation exception handled")

                    throw exception
                }
                println("Handled $exception")
            }
            println("Coroutine for result1 is still running")
        }
        delay(200)
        result1.cancel()
    }

    //Sleep the main thread to prevent function exit
    Thread.sleep(1000)
}

private suspend fun networkRequest1(): String {
    return withContext(Dispatchers.IO) {
        // Perform some operation
        //throw Exception()
        delay(400)
        return@withContext "Result 1"
    }
}
