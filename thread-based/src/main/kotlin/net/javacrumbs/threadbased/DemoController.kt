package net.javacrumbs.threadbased

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient


@RestController
class DemoController {
    @GetMapping("/demo")
    fun demo(
        @RequestParam(defaultValue = "100") delay: Long,
        @RequestParam(defaultValue = "false") log: Boolean
    ): Result {
        if (log) logger.info { "Will generate random number" }
        try {
            val randomNumber = getRandomNumber(delay)
            return Result(randomNumber.number)
        } catch (e: Exception) {
            logger.error(e) { "Error when generating random number" }
            throw e
        } finally {
            if (log) logger.info { "Finished" }
        }
    }

    private fun getRandomNumber(delay: Long): RandomNumber {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java).block()!!
    }

    companion object: KLogging()
}
