package com.example.mobileproject.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApiService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("type") type: String = "multiple",
        @Query("encode") encoding: String = "url3986"
    ): TriviaResponse
}
