package net.javacrumbs.reactorbased

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@RestController
class ReactorController {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()


    @GetMapping("/demoReactor")
    fun demo(): Mono<Result> {
        return getRandomNumber().map {
            Result(it.number * 2)
        }
    }

    private fun getRandomNumber(): Mono<RandomNumber> {
        return webClient.get().uri("/random").retrieve().bodyToMono(RandomNumber::class.java)
    }

}
