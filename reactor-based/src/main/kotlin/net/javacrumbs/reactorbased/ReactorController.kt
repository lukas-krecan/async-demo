package net.javacrumbs.reactorbased

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@RestController
class ReactorController {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()


    @GetMapping("/demoReactor")
    fun demo(@RequestParam(defaultValue = "50") delay: Long): Mono<Result> {
        return getRandomNumber(delay).map {
            Result(it.number * 2)
        }
    }

    private fun getRandomNumber(delay: Long): Mono<RandomNumber> {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java)
    }

}
