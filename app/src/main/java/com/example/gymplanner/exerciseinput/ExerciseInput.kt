package com.example.gymplanner.exerciseinput

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Main composable function for the Workout Tracker App.
 *
 * @see [https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-1]
 */

/**
 * Composable function for adding exercises to the Workout Tracker App.
 *
 * This composable provides a simple form for users to input exercise details such as
 * exercise name, number of reps, and weight. Users can add exercises, and the added
 * exercises are displayed in a list below the input form.
 *
 * @param db Firebase Firestore instance for storing exercise data.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WorkoutInput(db: FirebaseFirestore) {
    // Used to hide the keyboard when the "Add Exercise" button is clicked.
    // @see [https://stackoverflow.com/questions/69124822/android-compose-show-and-hide-keyboard]
    val keyboard = LocalSoftwareKeyboardController.current

    // State variables to store input values and the list of exercises.
    var exerciseName by rememberSaveable { mutableStateOf("") }
    var exerciseReps by rememberSaveable { mutableStateOf("") }
    var exerciseWeight by rememberSaveable { mutableStateOf("") }
    var exercisesList by rememberSaveable { mutableStateOf(emptyList<String>()) }


    // Scaffold provides the basic structure of the screen, including the app bar.
    Scaffold(
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        // Box is used to center the content on the screen.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center
        ) {
            // Column is a vertical arrangement of composables.
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Input fields for exercise details.
                OutlinedTextField(
                    value = exerciseName,
                    onValueChange = { exerciseName = it },
                    label = { Text("Exercise Name") },
                    singleLine = true,
                    modifier = Modifier.semantics { contentDescription = "Exercise Name" }
                )

                OutlinedTextField(
                    value = exerciseReps,
                    onValueChange = { exerciseReps = it },
                    label = { Text("Reps") },
                    singleLine = true,
                    modifier = Modifier.semantics { contentDescription = "Reps" }
                )

                OutlinedTextField(
                    value = exerciseWeight,
                    onValueChange = { exerciseWeight = it },
                    label = { Text("Weight") },
                    singleLine = true,
                    modifier = Modifier.semantics { contentDescription = "Weight" }
                )

                // Button to add an exercise.
                Button(
                    onClick = {
                        if (exerciseName.isNotEmpty() && exerciseReps.isNotEmpty() && exerciseWeight.isNotEmpty()) {
                            exercisesList = exercisesList + "$exerciseName - $exerciseReps reps - $exerciseWeight lbs"
                            exerciseName = ""
                            exerciseReps = ""
                            exerciseWeight = ""
                        }
                        keyboard?.hide()
                    },
                    modifier = Modifier
                        .size(width = 100.dp, height = 58.dp)
                        .padding(top = 12.dp)
                        .semantics { contentDescription = "Add Exercise Button" }
                ) {
                    Text("Add")
                }

                LazyColumn {
                    items(exercisesList) { exercise ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Exercise Name
                                Text(
                                    exercise,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 16.dp).semantics { contentDescription = "Exercise: $exercise" }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}