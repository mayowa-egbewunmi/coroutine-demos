package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*

/**
 * Also what happens if the exception is thrown in a child coroutine,
 *
 * So why does this happen ?
 *
 * rootScope <------- parentCoroutineJob <------ childCoroutineJob
 */
fun main() {
    val rootScope = CoroutineScope(Job())
    try {
        rootScope.launch {
            try {
                launch {
                    fetchDataFromAPI()
                }
            } catch (exception: Exception) {
                println("Handle $exception")
            }
        }
    } catch (exception: Exception) {
        println("Handle $exception in parent scope")
    }


    //Sleep the main thread to prevent function exit
    Thread.sleep(1000)
}

private suspend fun fetchDataFromAPI() {
    withContext(Dispatchers.IO) {
        // Make a network request
        throw  Exception()
    }
}