package net.javacrumbs.threadbased

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient


@RestController
class DemoController {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()

    @GetMapping("/demo")
    fun demo(): Any {
        val randomNumber = getRandomNumber()
        return Result(randomNumber.number)
    }

    private fun getRandomNumber(): RandomNumber {
        return webClient.get().uri("/random").retrieve().bodyToMono(RandomNumber::class.java).block()!!
    }

}
