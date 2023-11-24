package com.example.workouttracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codelabs.state.ToDoScreen
import com.example.workouttracker.exerciseinput.WorkoutInput
import com.example.workouttracker.exerciselist.ExerciseList
import com.example.workouttracker.exerciselist.WorkoutList
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.lang.reflect.Executable


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutTrackerTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination

                val currentScreen =
                    exerciseTabRowScreens.find { it.route == currentDestination?.route }
                        ?: ExerciseInput
                var showLandingScreen by remember { mutableStateOf(true) }
                if (showLandingScreen) {
                    LandingScreen(onTimeout = { showLandingScreen = false })
                } else {
                    Scaffold(topBar = {
                        ExerciseTabRow(
                            allScreens = exerciseTabRowScreens,
                            onTabSelected = { newScreen -> navController.navigateSingleTo(newScreen.route) },
                            currentScreen = currentScreen,
                        )
                    }) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = ExerciseInput.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(route = ExerciseInput.route) {
                                WorkoutInput()
                            }
                            composable(route = ExerciseList.route) {
                                WorkoutList()
                            }
                            composable(route = Notes.route) {
                                ToDoScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
fun NavHostController.navigateSingleTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }