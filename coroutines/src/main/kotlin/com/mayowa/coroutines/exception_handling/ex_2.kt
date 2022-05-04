package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*

/**
 * Can we also use a try catch to handle exception in coroutine?
 */
fun main() {
    val rootScope = CoroutineScope(Job())
    rootScope.launch {
        fetchDataFromAPI()
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