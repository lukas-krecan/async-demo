package net.javacrumbs.reactorbased

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient



@RestController
class CoroutineController {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()

    @GetMapping("/demoCoroutines")
    suspend fun demo(@RequestParam(defaultValue = "50") delay: Long): Result {
        val randomNumber = getRandomNumber(delay)
        return Result(randomNumber.number)
    }

    private suspend fun getRandomNumber(delay: Long): RandomNumber {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java).awaitSingle()
    }
}
