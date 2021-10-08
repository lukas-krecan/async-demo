package net.javacrumbs.threadbased

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.time.Duration


@RestController
class DemoController {
    private var client = createClient()

    @GetMapping("/demo")
    fun getRandomNumber(): Any {
        val randomNumber = client.getRandomNumber().execute().body()!!.number
        return Result(randomNumber)
    }

    private fun createClient(): Client {
        val dispatcher = Dispatcher().apply {
            maxRequests = 1000
            maxRequestsPerHost = 1000
        }

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(Duration.ofSeconds(10))
            .dispatcher(dispatcher)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(JacksonConverterFactory.create(Jackson2ObjectMapperBuilder.json().build()))
            .client(okHttpClient)
            .build().create(Client::class.java)
    }
}
