package net.javacrumbs.reactorbased

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@RestController
class ReactorControllerBad {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()


    @GetMapping("/demoReactorBad")
    fun demo(
        @RequestParam(defaultValue = "100") delay: Long,
        @RequestParam(defaultValue = "false") log: Boolean
    ): Mono<Result> {
        if (log) logger.info { "Will generate random number" }
        try {
            return getRandomNumber(delay).map {
                Result(it.number * 2)
            }
        } catch (e: Exception) {
            logger.error(e) { "Error when generating random number" }
            throw e
        } finally {
            if (log) logger.info { "Finished" }
        }
    }

    private fun getRandomNumber(delay: Long): Mono<RandomNumber> {
        return webClient.get().uri("/random?delay={delay}", delay)
            .retrieve().bodyToMono(RandomNumber::class.java)
    }

    companion object: KLogging()
}
