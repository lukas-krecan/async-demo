package net.javacrumbs.reactorbased

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@RestController
class ReactorController {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()


    @GetMapping("/demoReactor")
    fun demo(
        @RequestParam(defaultValue = "100") delay: Long,
        @RequestParam(defaultValue = "false") log: Boolean
    ): Mono<Result> {
        if (log) logger.info { "Will generate random number" }
        return getRandomNumber(delay).map {
            if (log) logger.info { "Random number generated" }
            Result(it.number * 2)
        }
    }

    private fun getRandomNumber(delay: Long): Mono<RandomNumber> {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java)
    }

    companion object: KLogging()
}
