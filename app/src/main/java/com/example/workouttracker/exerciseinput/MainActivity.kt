package com.example.workouttracker.exerciseinput

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    WorkoutInput()
                }
            }
        }
    }
}


/**
 * Main composable function for the Workout Tracker App.
 *
 * @see [https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-1]
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WorkoutInput() {
    // used to hide the keyboard when add exercise button is clicked
    // @see [https://stackoverflow.com/questions/69124822/android-compose-show-and-hide-keyboard]
    val keyboard = LocalSoftwareKeyboardController.current

    var exerciseName by rememberSaveable { mutableStateOf("") }
    var exerciseReps by rememberSaveable { mutableStateOf("") }
    var exerciseWeight by rememberSaveable { mutableStateOf("") }
    var exercisesList by rememberSaveable { mutableStateOf("") }


    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            // @see [https://semicolonspace.com/jetpack-compose-images/]
            Image(painter = painterResource(R.drawable.logo), contentScale = ContentScale.Crop, contentDescription = "Logo", modifier = Modifier
                .size(70.dp)
                .clip(
                    CircleShape
                )
                .border(width = 2.dp, color = Color.Blue, shape = CircleShape))},
            title = {Text("Workout Tracker")})},
        contentColor = MaterialTheme.colorScheme.primary
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it), contentAlignment = Alignment.Center)
        {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = exerciseName,
                    onValueChange = { exerciseName = it },
                    label = { Text("Exercise Name") },
                    singleLine = true
                )

                TextField(
                    value = exerciseReps,
                    onValueChange = { exerciseReps = it },
                    label = { Text("Reps") },
                    singleLine = true
                )

                TextField(
                    value = exerciseWeight,
                    onValueChange = { exerciseWeight = it },
                    label = { Text("Weight") },
                    singleLine = true
                )

                Button(onClick = {
                    if (exerciseName.isNotEmpty() && exerciseReps.isNotEmpty() && exerciseWeight.isNotEmpty()) {
                        exercisesList += "$exerciseName - $exerciseReps reps - $exerciseWeight lbs\n"
                        exerciseName = ""
                        exerciseReps = ""
                        exerciseWeight = ""
                    }
                    keyboard?.hide()
                },
                    modifier = Modifier.size(width = 100.dp, height = 48.dp)
                ) {
                    Text("Add Exercise")
                }

                LazyColumn {
                    items(exercisesList.split("\n")) { exercise ->
                        Text(exercise)
                    }
                }
            }
        }
    }
}



/**
 * Preview function for the Workout Tracker App
 */
@Preview
@Composable
fun PreviewWorkoutTrackerApp() {
    WorkoutInput()
}
