package com.example.quizapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.QuizResponse
import com.example.quizapp.network.quizApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class QuizState(
    val question : String? = null,
    val options : List<String>? = null
)

class QuizViewModel : ViewModel() {
    private var index = 0
    private var _quizState = MutableStateFlow(QuizState())
    val quizState = _quizState.asStateFlow()

    private var correctAnswer: String? = null

    // MutableStateFlow to hold quiz data
    private val _quizResponse = MutableStateFlow<QuizResponse?>(null)

    // StateFlow to expose data to the UI as a read-only flow
    val quizResponse: StateFlow<QuizResponse?> = _quizResponse
    var score = 0
    init {
        getData()
    }

    // Function to fetch quiz data from the API
    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = quizApi.value.getQuiz(10, 9, "easy", "multiple")
                _quizResponse.value = data  // Assign the fetched data to _quizResponse

                // Now that data is ready, call nextQuestion()
                viewModelScope.launch(Dispatchers.Main) {
                    nextQuestion()  // Ensure it's run on the main thread
                }
            } catch (e: Exception) {
                _quizResponse.value = null
                // Handle errors appropriately
            }
        }
    }

    fun checkAnswerAndProceed(answer:String){
        if (answer ==  correctAnswer){
            score ++
            println("hurray correct answer!! score is $score")
        }
        else
            println("oops wrong one! score is $score")
        nextQuestion()

    }




    fun nextQuestion() {

        println(_quizState.value)

        val question = _quizResponse.value?.results?.getOrNull(index)?.question
        val answer = _quizResponse.value?.results?.getOrNull(index)?.correct_answer
        var options = _quizResponse.value?.results?.getOrNull(index)?.incorrect_answers?.toMutableList()

        // Check if we have valid data before proceeding
        if (question != null && answer != null && options != null) {
            options.add(answer)  // Add the correct answer to the options list
            val shuffledOptions = options.shuffled()
            _quizState.value = QuizState(question = question, options = shuffledOptions)
            correctAnswer = answer
            index++
        } else {
            // Handle end of quiz or invalid data
        }
    }
}
