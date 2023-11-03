package com.example.workouttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codelabs.state.ToDoScreen
import com.example.workouttracker.exerciseinput.WorkoutInput
import com.example.workouttracker.exerciselist.WorkoutList
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigator()
                }
            }
        }
    }
}



@Composable
fun MainNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "exerciseinput") {
        composable("exerciseinput") {
            ExerciseInputScreen(navController)
        }
        composable("exerciselist") {
            ExerciseListScreen(navController)
        }
        composable("notes") {
            NotesScreen(navController)
        }
    }
}

@Composable
fun ExerciseInputScreen(navController: NavController) {
    Column {
        SquareButton(onClick = {
            navController.navigate("exerciselist")
        }) {
            Text("List")
        }
        SquareButton(onClick = {
            navController.navigate("notes")
        }) {
            Text("Notes")
        }
        WorkoutInput()
    }
}

@Composable
fun ExerciseListScreen(navController: NavController) {
    Column {
        SquareButton(onClick = {
            navController.navigate("exerciseinput")
        }) {
            Text("Input")
        }
        SquareButton(onClick = {
            navController.navigate("notes")
        }) {
            Text("Notes")
        }
        WorkoutList()
    }

}

@Composable
fun NotesScreen(navController: NavController) {
    Column {
        SquareButton(onClick = {
            navController.navigate("exerciseinput")
        }) {
            Text("Input")
        }
        SquareButton(onClick = {
            navController.navigate("exerciselist")
        }) {
            Text("List")
        }
        ToDoScreen()
    }
}

@Composable
fun SquareButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(start = 4.dp)
    ) {
        content()
    }
}
