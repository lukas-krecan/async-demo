package net.javacrumbs.threadbased

import retrofit2.Call
import retrofit2.http.GET


interface Client {
    @GET("/random")
    fun getRandomNumber(): Call<RandomNumber>
}

data class RandomNumber(val number: Double)
