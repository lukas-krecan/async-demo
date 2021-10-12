package net.javacrumbs.loom

import mu.KLogging
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors
import java.util.concurrent.atomic.LongAdder

class LoomTest {

    @Test
    fun `Should handle lot of threads`() {
        val adder = LongAdder()
        logger.info { "Started" }

        //Create a new virtual thread executor,
        // `use` is Kotlin way to write try with resources
        // `close` method on the executor waits for all submitted tasks
        Executors.newVirtualThreadExecutor().use { executor ->
            repeat(1_000_000) {
                executor.submit {
                    //logger.info { "Hello from a virtual thread" }
                    Thread.sleep(100)
                    adder.increment()
                }
            }
        }
        logger.info { "The result is ${adder.sum()}" }
    }

    companion object: KLogging()
}
