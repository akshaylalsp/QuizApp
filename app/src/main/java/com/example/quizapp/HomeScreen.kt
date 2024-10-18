package com.example.quizapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(modifier: Modifier = Modifier.fillMaxSize(),viewModel: QuizViewModel = QuizViewModel()){
    QuizItemComponent(modifier = modifier.fillMaxSize(),viewModel = viewModel)

}



@Composable
fun QuizItemComponent(modifier: Modifier = Modifier,viewModel: QuizViewModel){

    var response = viewModel.quizResponse.collectAsState(initial = null)
    var quiz = viewModel.quizState.collectAsState(initial = null)
    println(response?.value?.response_code)

    var selectedItem by remember {
        mutableStateOf(false)
    }

    if (response.value != null ||  response?.value?.response_code == 0){
        Column(modifier = modifier.padding(20.dp)) {

            Row(modifier = Modifier.fillMaxWidth() ) {
//                quiz?.value?.question?.get(0)?.let { Text(text = it.question) }
                Text(text = quiz?.value?.question?:"no data available")
            }
            quiz.value?.options?.forEach {
                OptionComponent(selectedItem = selectedItem, option = it){
                    selectedItem = !selectedItem
                }
            }





            Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.SpaceAround){

                Button(onClick = { selectedItem = false }) {
                    Text(text = "clear")
                }
                Button(onClick = { viewModel.nextQuestion() }) {
                    Text(text = "submit")
                }
            }
        }
    }
    else
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue), contentAlignment = Alignment.Center,){ Text(modifier = Modifier.fillMaxSize(), color = Color.Green, text = "no data available") }







        Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.SpaceAround){

            Button(onClick = { selectedItem = false }) {
                Text(text = "clear")
            }
            Button(onClick = { viewModel.nextQuestion() }) {
                Text(text = "submit")
            }
        }
    }



@Composable
fun OptionComponent(selectedItem:Boolean,option:String,onClick:()->Unit,){
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() },
        horizontalArrangement = Arrangement.Absolute.Left, verticalAlignment = Alignment.CenterVertically
    ) {
        Box{ RadioButton(selected = selectedItem, onClick = onClick) }
        Box{ Text(text = option) }
    }
}






@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}