package com.mayowa.coroutines.exception_handling

/**
 * Write asynchronous and concurrent code in a sequential fashion
 * The goal of coroutine is for it to be intuitive.
 * For the most part of Kotlin coroutine are intuitive because it uses
 * conventional coding constructs and code is written sequential fashion
 *
 * However, Kotlin Coroutine is not that intuitive with Exception Handling
 */
fun main() {
    //try {
        functionThatThrows()
//    } catch (exception: Exception) {
//        println("handled $exception")
//    }

}

private fun functionThatThrows() {
    // some code
    throw  Exception()
}