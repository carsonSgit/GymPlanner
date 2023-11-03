@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.example.workouttracker.exerciselist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutTrackerTheme {
                Surface (modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background){
                    Scaffold (topBar = { TopAppBar(title = { Text("The Gym App")})},
                        bottomBar = {
                            BottomAppBar (containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary,){
                                Text(modifier = Modifier
                                    .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = "Thank You for visiting")
                            }
                        }){
                            innerPadding ->
                        Column(modifier = Modifier
                            .padding(innerPadding),
                            verticalArrangement = Arrangement.spacedBy(16.dp),)
                        {

                            WorkoutList(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }


}







// provides a list of exercises to be displayed
@Composable
fun ExerciseList(input: String){

    val legs = "legs"
    val arms = "arms"
    val chest = "chest"
    val back = "back"

    val bodyPart = input.lowercase()

    val legExercises = listOf("Squats", "Leg Extension", "Hamstring Curl")
    val armExercises = listOf("Bicep Curls", "Triceps Pushdowns","Wrist Curls" )
    val chestExercises = listOf("Bench Press", "Pec Fly", "Incline bench Press")
    val backExercises = listOf("Close Grip Row","Pull Up","Deadlift")



    if(bodyPart == legs){
        LazyColumn(contentPadding = PaddingValues(16.dp)){
            items(legExercises) {item ->

                Spacer(modifier = Modifier.height(100.dp))
                Text(text = item)
            }
        }
    }
    if(bodyPart == arms){
        LazyColumn(contentPadding = PaddingValues(16.dp)){
            items(armExercises) {item ->

                Spacer(modifier = Modifier.height(100.dp))
                Text(text = item)
            }
        }
    }
    if(bodyPart == chest){
        LazyColumn(contentPadding = PaddingValues(16.dp)){
            items(chestExercises) {item ->

                Spacer(modifier = Modifier.height(100.dp))
                Text(text = item)
            }
        }
    }
    if(bodyPart == back){
        LazyColumn(contentPadding = PaddingValues(16.dp)){
            items(backExercises) {item ->

                Spacer(modifier = Modifier.height(100.dp))
                Text(text = item)
            }
        }
    }

}



// greets the user showing the user's name
@Composable
fun Greeting( modifier: Modifier = Modifier) {
    val expanded = remember {
        mutableStateOf(false)
    }
    var userName by remember {
        mutableStateOf("")
    }
    var exerciseChoice by remember { mutableStateOf("")}
    
    val extraPadding = if(expanded.value) 48.dp else 0.dp

    Surface (color = Color.LightGray,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)) {
                Text(text = "Enter Your Name:")
                OutlinedTextField(value = userName, onValueChange = {userName = it} ,
                    label = { Text("User Name")})
                Text(
                    text = "Hello $userName!"
                )
                LineBreak
                Text(text = "What do you want to train today $userName")
                OutlinedTextField(value = exerciseChoice , onValueChange = {exerciseChoice = it}, label = { Text(
                    text = "Exercise Choice"
                )})
                ExerciseList(input = exerciseChoice)

            }
        }
    }

}

// an extra screen that lets you onto the app
@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit,modifier: Modifier = Modifier){


    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Welcome to the gym")

        Button(modifier = Modifier.padding(vertical = 24.dp),onClick = onContinueClicked) {
            Text(text = "Continue to the gym")

        }
    }
}

@Composable
fun WorkoutList(modifier: Modifier = Modifier){
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(modifier
        .background(MaterialTheme.colorScheme.background)) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greeting()

        }}
}


// preview for the onboarding screen
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(){
    WorkoutTrackerTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}



// preview for the greeting
@Preview(showBackground = true, widthDp = 320, heightDp = 200)
@Composable
fun GreetingPreview() {
    WorkoutTrackerTheme {
        Greeting()
    }
}

// preview for the gym app
@Preview
@Composable
fun GymAppPreview(){
    WorkoutTrackerTheme {
        WorkoutList(Modifier.fillMaxSize())
    }

}