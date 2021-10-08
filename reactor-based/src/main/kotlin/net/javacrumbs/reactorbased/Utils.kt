package net.javacrumbs.reactorbased

import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.time.Duration

object Utils {
    fun <T> createClient(klass: Class<T>): T {
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
            .addCallAdapterFactory(ReactorCallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(klass)
    }
}
