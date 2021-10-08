package net.javacrumbs.reactorbased

import net.javacrumbs.reactorbased.Utils.createClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import retrofit2.http.GET
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage


@RestController
class CompletionStageController {

    private var client = createClient(Client::class.java)

    @GetMapping("/demoFuture")
    fun demos(): CompletableFuture<Result> {
        return client.getRandomNumber().thenApply {
            Result(it.number * 2)
        }
    }

    private interface Client {
        @GET("/random")
        fun getRandomNumber(): CompletableFuture<RandomNumber>
    }
}
