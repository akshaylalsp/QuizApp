package com.example.quizapp.data

data class QuizResponse(
    val response_code: Int,
    val results: List<Quiz>
)

data class Quiz(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)
