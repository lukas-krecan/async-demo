package net.javacrumbs.reactorbased

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient



@RestController
class CoroutineController {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()

    @GetMapping("/demoCoroutines")
    suspend fun demo(): Result {
        val randomNumber = getRandomNumber()
        return Result(randomNumber.number)
    }

    private suspend fun getRandomNumber(): RandomNumber {
        return webClient.get().uri("/random").retrieve().bodyToMono(RandomNumber::class.java).awaitSingle()
    }
}
