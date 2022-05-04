package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*

/**
 * Wrap an exception inside async with try/catch if a fallback is required for the operation
 * Otherwise, the 'await" method call should be wrapped instead with the try/catch
 */
fun main() {
    val rootScope = CoroutineScope(Job())
    val deferred = rootScope.async {
        println("Async Work Started")
        throw Exception()
    }

//    runBlocking {
//        deferred.await()
//    }
}