package net.javacrumbs.threadbased

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future


@RestController
class ParallelThreadsDemoController {
    private val executor = Executors.newFixedThreadPool(100);

    @GetMapping("/demoSumThreads")
    fun demo(
        @RequestParam(defaultValue = "3") n: Int,
        @RequestParam(defaultValue = "100") delay: Long,
        @RequestParam(defaultValue = "false") log: Boolean
    ): Result {
        if (log) logger.info { "Will generate sum of random numbers" }
        try {
            val futures: List<Future<RandomNumber>> = (0..n).map {
                // This is really wasteful, do not do this.
                executor.submit(Callable { getRandomNumber(delay) })
            }
            val result = futures.sumOf {
                // Blocking on get
                it.get().number
            }
            return Result(result)
        } catch (e: Exception) {
            logger.error(e) { "Error when generating random number" }
            throw e
        } finally {
            if (log) logger.info { "Finished" }
        }
    }

    private fun getRandomNumber(delay: Long): RandomNumber {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java)
            .block()!!
    }

    companion object : KLogging()
}
