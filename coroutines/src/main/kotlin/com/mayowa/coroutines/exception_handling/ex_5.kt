package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

/**
 * When should we use Coroutine Exception Handler vs Try/Catch
 *
 * 1. Coroutine Exception Handler is a Global Catch all while Try/Catch handles exception directly in the Coroutine
 *
 * 2. Coroutine Exception Handler cannot be used for recovery while Try/Catch can be used for Recovery
 *
 * 3. Use Coroutine Exception Handler for exception logging and showing generic error message,
 * use Try/Catch for retry operations, fallback operations
 */

fun main() {
    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("Handled $exception in CoroutineExceptionHandler")
    }

    val rootScope = CoroutineScope(Job())
    rootScope.launch(coroutineExceptionHandler) {
        launch {
            try {
                fetchDataFromAPI()
            } catch (exception: Exception) {
                println("Handled $exception in a Try/Catch")
                //Performing fallback operation
                readDataFromDB() //Note that this will also throw exception but will be handled by coroutineExceptionHandler
            }
        }
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

private suspend fun readDataFromDB() {
    withContext(Dispatchers.IO) {
        // Read data from DB
        println("Reading data from DB")
        throw IllegalArgumentException()
    }
}