package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*

/**
 * How can handle exception in coroutine?
 *
 * Coroutine exception handler
 *
 * Every Coroutine and CoroutineScope has a CoroutineContext
 *
 * CoroutineContext Elements = Dispatcher + Job + Name + ExceptionHandler
 *
 * Install Exception handler either in the Root Coroutine or coroutineScope
 * Note we can't install exception handler on child coroutine
 */
fun main() {
    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("Handled $exception in CoroutineExceptionHandler")
    }

    val rootScope = CoroutineScope(Job()) // root node
    rootScope.launch(coroutineExceptionHandler) {
        launch {
            fetchDataFromAPI()
        }
        //vs launch {... }
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
