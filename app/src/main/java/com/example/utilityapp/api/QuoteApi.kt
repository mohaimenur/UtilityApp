package com.example.utilityapp.api

//QuoteApi: zenquotes.io/api/quotes

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


data class QuoteResponse(
    val q: String, // The quote text
    val a: String // The author name

)

interface QuoteApi {
    @GET("api/quotes")
    suspend fun getQuotes(): List<QuoteResponse>

}

// RetrofitInstance is an object that creates a single, static instance (a singleton)

object RetrofitInstance {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val api: QuoteApi by lazy {//defers the initialization of the variable until it is first accessed.
        Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApi::class.java)

    }

}