package net.javacrumbs.threadbased

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Future


@RestController
class DemoSumAsyncController {

    @GetMapping("/demoSum")
    fun demo(
        @RequestParam(defaultValue = "3") n: Int,
        @RequestParam(defaultValue = "100") delay: Long,
        @RequestParam(defaultValue = "false") log: Boolean
    ): Result {
        if (log) logger.info { "Will generate sum of random numbers" }
        try {
            val result = (0..n).map {
                getRandomNumber(delay)
            }.sumOf {
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

    private fun getRandomNumber(delay: Long): Future<RandomNumber> {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java)
            .toFuture()
    }

    companion object : KLogging()
}
