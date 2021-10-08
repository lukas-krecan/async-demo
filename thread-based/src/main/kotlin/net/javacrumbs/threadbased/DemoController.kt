package net.javacrumbs.threadbased

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


@RestController
class DemoController {
    private var client = Retrofit.Builder()
        .baseUrl("http://localhost:8080")
        .addConverterFactory(JacksonConverterFactory.create(Jackson2ObjectMapperBuilder.json().build()))
        .build().create(Client::class.java)

    @GetMapping("/demo")
    fun getRandomNumber(): Any {
        val randomNumber = client.getRandomNumber().execute().body()!!.number
        return mapOf("result" to randomNumber * 2)
    }
}
