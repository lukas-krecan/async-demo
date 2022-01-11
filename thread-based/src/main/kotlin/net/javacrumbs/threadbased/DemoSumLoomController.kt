package net.javacrumbs.threadbased

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Callable
import java.util.concurrent.Executors


@RestController
class DemoSumLoomController {

    @GetMapping("/demoSumLoom")
    fun demo(
        @RequestParam(defaultValue = "3") n: Int,
        @RequestParam(defaultValue = "100") delay: Long,
        @RequestParam(defaultValue = "false") log: Boolean
    ): Result {
        if (log) logger.info { "Will generate sum of random numbers" }
        try {
            return Executors.newVirtualThreadPerTaskExecutor().use { executor ->
                val result = (0..n).map {
                    executor.submit(Callable { getRandomNumber(delay, log) })
                }.sumOf {
                    // Blocking on get
                    it.get().number
                }
                Result(result)
            }
        } catch (e: Exception) {
            logger.error(e) { "Error when generating random number" }
            throw e
        } finally {
            if (log) logger.info { "Finished" }
        }
    }

    private fun getRandomNumber(delay: Long, log: Boolean): RandomNumber {
        if (log) logger.info { "Generating random number" }
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java)
            .block()!!
    }

    companion object : KLogging()
}
