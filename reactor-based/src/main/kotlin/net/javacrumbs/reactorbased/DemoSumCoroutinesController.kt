package net.javacrumbs.reactorbased

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class DemoSumCoroutinesController {

    @GetMapping("/demoSumCoroutines")
    suspend fun demo(
        @RequestParam(defaultValue = "3") n: Int,
        @RequestParam(defaultValue = "100") delay: Long,
        @RequestParam(defaultValue = "false") log: Boolean
    ): Result = coroutineScope {
        if (log) logger.info { "Will generate sum of random numbers" }
        try {
            val result = (0..n).map {
                async { getRandomNumber(delay) }
            }.sumOf {
                it.await().number
            }
            Result(result)
        } catch (e: Exception) {
            logger.error(e) { "Error when generating random number" }
            throw e
        } finally {
            if (log) logger.info { "Finished" }
        }
    }

    private suspend fun getRandomNumber(delay: Long): RandomNumber {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java)
            .awaitSingle()
    }

    companion object : KLogging()
}
