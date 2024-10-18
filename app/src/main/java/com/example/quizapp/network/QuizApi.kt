package com.example.quizapp.network

import com.example.quizapp.data.Quiz
import com.example.quizapp.data.QuizResponse

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


val retrofit = Retrofit.Builder()

    .baseUrl("https://opentdb.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface QuizApiInterface{
    @GET("api.php")
    suspend fun getQuiz(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): QuizResponse
}

val quizApi = lazy { retrofit.create(QuizApiInterface::class.java) }

