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

        val executor = Executors.newThreadPerTaskExecutor(::Thread)
        repeat(1_000) {
            executor.submit {
                Thread.sleep(1_000)
                adder.increment()
            }
        }
        // `close` method on the executor waits for all submitted tasks
        // Should be used in `use` (try with resources)
        executor.close()

        logger.info { "The result is ${adder.sum()}" }
    }

    companion object : KLogging()
}
