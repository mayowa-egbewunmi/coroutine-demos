package com.mayowa.coroutines.exception_handling

import kotlinx.coroutines.*
import java.lang.Exception

/**
 * Using supervisorScope for exception handling
 *
 * The major difference between supervisorScope and coroutineScope that a coroutineScope will cancel
 * whenever any of its children fail. If we want to continue with the other tasks even when one fails,
 * we go with the supervisorScope.
 */

fun main() {
    runBlocking {
//        launch {
//            supervisorScope {
//                launch {
//                    throw Exception()
//                }
//                launch {
//                    for (n in 0..20) {
//                        println("print $n")
//                        delay(100)
//                    }
//                }
//            }
//        }

        launch {
            coroutineScope {
                launch {
                    throw Exception()
                }
                launch {
                    for (n in 0..20) {
                        println("print $n")
                        delay(100)
                    }
                }
            }
        }
    }

}