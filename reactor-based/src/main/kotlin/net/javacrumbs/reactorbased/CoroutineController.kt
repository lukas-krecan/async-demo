package net.javacrumbs.reactorbased

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.http.GET


@RestController
class CoroutineController {
    private var client = Utils.createClient(Client::class.java)

    @GetMapping("/demoCoroutines")
    suspend fun demo(): Result {
        val randomNumber = client.getRandomNumber().number
        return Result(randomNumber)
    }

    private interface Client {
        @GET("/random")
        suspend fun getRandomNumber(): RandomNumber
    }
}
