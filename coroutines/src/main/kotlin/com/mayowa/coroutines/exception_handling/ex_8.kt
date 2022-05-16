package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*

fun main2() = runBlocking {
    val def1 = async {
        throw RuntimeException("def1")
    }
    
}

/**
 * Wrap an exception inside async with try/catch if a fallback is required for the operation
 * Otherwise, the 'await" method call should be wrapped instead with the try/catch
 */
fun main() = runBlocking {

    val rootScope = CoroutineScope(Job())
    
    val deferred = async {
        println("Async 1 started")
        throw Exception()
        "result"
    }
    
    delay(500)
    println("Async Coroutine is ${deferred.isActive}")
    println("Root scope is ${rootScope.isActive}")

    delay(500)

    //println("${deferred.getCompletionExceptionOrNull()}")
    

//    val deferred2 = rootScope.async {
//        delay(500)
//        println("Async 2 started")
//        "result"
//    }
//
//    val deferred = rootScope.async {
//        println("Async Work Started")
//        throw Exception()
//        println("Async Work Ended")
//    }
//    Thread.sleep(1000)
//    runBlocking {
//        println("worker is completed: ${deferred.isCompleted}")
//        try {
//            deferred.await()
//        } catch (ex: Exception) {
//            println("Exception $ex")
//        }
//    }


//    runBlocking {
//        try {
//            deferred.await()
//        } catch (ex: Exception) {
//            println("Handled $ex")
//        }
//    }
//    rootScope.launch {
//
//    }
//    val deferred = rootScope.async {
//        println("Async Work Started")
//        throw Exception()
//    }
//        runBlocking {
//            deferred.await()
//        }


//    val result1 = rootScope.async {
//        println("Start 1")
//        delay(1000)
//        1
//    }
//
//    val result2 = rootScope.async {
//        println("Start 2")
//        delay(1000)
//        2
//    }
//
//    rootScope.launch {
//        val startTime = System.currentTimeMillis()
//        printSum(result1.await(), result2.await())
//        println("execution time is: ${System.currentTimeMillis() - startTime}")
//    }
//
//
//    rootScope.launch {
//        val startTime = System.currentTimeMillis()
//        val n1 = result1.await()
//        val n2 = result2.await()
//        printSum(n1, n2)
//        println("*** execution time is: ${System.currentTimeMillis() - startTime}")
//    }

//    Thread.sleep(2000)

}

fun printSum(num1: Int, num2: Int) {
    println("Result is ${num1 + num2}")
}