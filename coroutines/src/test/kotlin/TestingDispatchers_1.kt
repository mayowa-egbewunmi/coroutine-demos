@file:JvmName("Point_4Kt")

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * When your code creates new coroutines other than the top-level test coroutine
 * that runTest creates, you’ll need to control how those new coroutines are scheduled
 * by choosing the appropriate TestDispatcher
 *
 * We have 2 TestDispatchers:
 * 1. StandardTestDispatcher
 * 2. UnconfinedTestDispatcher
 *
 * To start the top-level test coroutine, runTest creates a TestScope,
 * which is an implementation of CoroutineScope that will always use a TestDispatcher.
 * If not specified, a TestScope will create a StandardTestDispatcher by default,
 * and use that to run the top-level test coroutine.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TestingDispatchers1 {

    /**
     * When you start new coroutines on a StandardTestDispatcher, they are queued up on the
     * underlying scheduler,to be run whenever the test thread is free to use. To let these new
     * coroutines run, you need to yield the test thread (free it up for other coroutines to use).
     */

    @Test
    fun standardTestAdvanceUntilIdle() = runTest {
        val users = mutableListOf<String>()

        register("Alice", users)
        advanceUntilIdle()

        assertEquals(listOf("Alice"), users) //
    }

    @Test
    fun standardTestAdvanceTimeBy() = runTest {
        val users = mutableListOf<String>()

        register("Alice", users)
        advanceTimeBy(501)

        assertEquals(listOf("Alice"), users) //
    }

    @Test
    fun standardTestRunCurrent() = runTest {
        val users = mutableListOf<String>()

        register("Bob", users)
        runCurrent()

        assertEquals(listOf("Bob"), users)
    }

    /**
     * When new coroutines are started on an UnconfinedTestDispatcher,
     * they are started eagerly on the current thread.
     *
     * What does it mean to be eagerly started?
     * UnconfinedTestDispatcher does not run test to completion eagerly
     *
     * Note that this behavior is different from what you’ll see in production with non-test dispatchers.
     * If your test focuses on concurrency, prefer using StandardTestDispatcher instead.
     *
     */
    @Test
    fun unconfinedTest() = runTest(UnconfinedTestDispatcher()) {
        val users = mutableListOf<String>()

        register("Alice", users)
        register("Bob", users)

        assertEquals(listOf("Alice", "Bob"), users) // ✅ Passes
    }
}

fun CoroutineScope.register(name: String, users: MutableList<String>) {
    launch {
        delay(500)
        users.add(name)
    }
}

