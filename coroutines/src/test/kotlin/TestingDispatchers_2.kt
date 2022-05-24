import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

/**
 * If your code moves the coroutine execution to other dispatchers(for example, by using withContext),
 * runTest will still generally work, but delays will no longer be skipped, and
 * tests will be less predictable as code runs on multiple threads.
 *
 * For these reasons, in tests you should inject test dispatchers to replace real dispatchers.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TestingDispatchers2 {

    /**
     * Coroutine test that calls delay would become flaky if a real dispatcher
     * is not replaced with a test dispatcher
     */
    @Test
    fun testThreadJumping() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val repository = Repository(dispatcher)

        repository.fetchData()

        assertEquals(Hello_World, repository.result)
    }

    @Test
    fun repoInitWorksAndDataIsHelloWorld() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val repository = Repository(dispatcher)

        repository.initialize()
        advanceUntilIdle() // Runs the new coroutine
        assertEquals(true, repository.initialized.get())

        repository.fetchData() // No thread switch, delay is skipped
        assertEquals(Hello_World, repository.result)
    }

    /**
     * The Repository design is not desirable in production code. Instead,
     * this method should be redesigned to be either suspending (for sequential execution),
     * or to return a Deferred value (for concurrent execution).
     */
    @Test
    fun repoDesignIsBadForProductionEnvironment() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val repository = Repository(dispatcher)
        advanceUntilIdle() // Advance since we launched a new coroutine on the scope - line 49 - 52

        assertNotNull(repository.result)
    }
}

class Repository(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    private val scope = CoroutineScope(ioDispatcher)
    val initialized = AtomicBoolean(false)

    var result: String? = null

    //TODO: Implement fetch on constructor instantiation

    // A function that starts a new coroutine on the IO dispatcher
    fun initialize() = scope.launch(ioDispatcher) {
        initialized.set(true)
    }

    // A suspending function that switches to the IO dispatcher
    suspend fun fetchData() = withContext(ioDispatcher) {
        require(initialized.get()) { "Repository should be initialized first" }
        delay(1000L)
        result =  Hello_World
    }
}

const val Hello_World = "Hello world"