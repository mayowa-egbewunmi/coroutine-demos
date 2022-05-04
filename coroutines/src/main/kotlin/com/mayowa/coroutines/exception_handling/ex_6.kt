package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*

/**
 * Sometimes it is okay to use try catch for logging
 * if you don't want the parent coroutine to be cancelled
 *
 */

fun main() {
    val rootScope = CoroutineScope(Job())
    rootScope.launch {
        val result1 = async {
            networkRequest1()
        }
        val result2 = async {
            try {
                networkRequest2()
            } catch (exception: Exception) {
                println("Handled $exception")
            }
        }
        awaitAll(result1, result2).forEach {
            println(it)
        }
    }

    //Sleep the main thread to prevent function exit
    Thread.sleep(1000)
}

private suspend fun networkRequest1(): String {
    return withContext(Dispatchers.IO) {
        // Perform some operation
        return@withContext "Result 1"
    }
}

private suspend fun networkRequest2(): String {
    return withContext(Dispatchers.IO) {
        // Perform some operation
        throw Exception("")
        return@withContext "Result 2"
    }
}