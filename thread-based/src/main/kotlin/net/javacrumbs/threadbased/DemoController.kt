package net.javacrumbs.threadbased

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient


@RestController
class DemoController {
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()

    @GetMapping("/demo")
    fun demo(@RequestParam(defaultValue = "50") delay: Long): Any {
        val randomNumber = getRandomNumber(delay)
        return Result(randomNumber.number)
    }

    private fun getRandomNumber(delay: Long): RandomNumber {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java).block()!!
    }

}
