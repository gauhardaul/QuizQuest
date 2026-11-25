package com.example.mobileproject.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal val triviaJson = Json { ignoreUnknownKeys = true }

object RetrofitInstance {
    val triviaApi: TriviaApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(triviaJson.asConverterFactory(MediaType.get("application/json")))
            .build()
            .create(TriviaApiService::class.java)
    }
}
