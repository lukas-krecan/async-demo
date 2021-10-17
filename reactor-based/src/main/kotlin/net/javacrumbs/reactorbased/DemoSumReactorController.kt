package net.javacrumbs.reactorbased

import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers


@RestController
class DemoSumReactorController {
    @GetMapping("/demoSumReactor")
    fun demo(
        @RequestParam(defaultValue = "3") n: Int,
        @RequestParam(defaultValue = "100") delay: Long,
        @RequestParam(defaultValue = "false") log: Boolean
    ): Mono<Result> {
        if (log) logger.info { "Will generate sum of random numbers" }

        return Flux.range(0, n)
            .parallel()
            .flatMap { getRandomNumber(delay) }
            .map { it.number }
            .reduce { x1, x2 -> x1 + x2 }
            .map { Result(it) }
            .onErrorMap { e ->
                logger.error(e) { "Error when generating random number" }
                e
            }.doFinally {
                if (log) logger.info { "Finished" }
            }
    }

    private fun getRandomNumber(delay: Long): Mono<RandomNumber> {
        return webClient.get().uri("/random?delay={delay}", delay).retrieve().bodyToMono(RandomNumber::class.java)
    }

    companion object : KLogging()
}
