package net.javacrumbs.reactorbased

import kotlinx.coroutines.reactor.awaitSingle
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient


@RestController
class CoroutineController {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()

    @GetMapping("/demoCoroutines")
    suspend fun demo(
        @RequestParam(defaultValue = "100") delay: Long,
        @RequestParam(defaultValue = "false") log: Boolean
    ): Result {
        if (log) logger.info { "Will generate random number" }
        try {
            val randomNumber = getRandomNumber(delay)
            if (log) logger.info { "Random number generated" }
            return Result(randomNumber.number)
        } catch (e: Exception) {
            logger.error(e) { "Error when generating random number" }
            throw e
        }
    }

    private suspend fun getRandomNumber(delay: Long): RandomNumber {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java).awaitSingle()
    }

    companion object: KLogging()
}
