package net.javacrumbs.randomnumberservice

import kotlinx.coroutines.delay
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ThreadLocalRandom

@RestController
class RandomNumberController {

    @GetMapping("/random")
    suspend fun getRandomNumber(@RequestParam(defaultValue = "50") delay: Long): Any {
        if (delay < 0) {
            throw IllegalArgumentException("Delay can not be negative")
        }
        delay(delay)
        return mapOf("number" to generateNumber())
    }

    private fun generateNumber() = ThreadLocalRandom.current().nextDouble(1.0)

}
