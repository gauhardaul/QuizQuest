package com.example.mobileproject.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitInstance {
    val triviaApi: TriviaApiService by lazy {
        val json = Json { ignoreUnknownKeys = true }

        Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
            .create(TriviaApiService::class.java)
    }
}
