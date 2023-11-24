package com.example.workouttracker

import androidx.compose.runtime.Composable
import com.codelabs.state.ToDoScreen
import com.example.workouttracker.exerciseinput.WorkoutInput
import com.example.workouttracker.exerciselist.WorkoutList

interface WorkoutDestination {
    val route: String
}

object ExerciseInput:WorkoutDestination{
    override val route = "exerciseinput"
}
object ExerciseList: WorkoutDestination{
    override val route = "exerciselist"

}

object Notes: WorkoutDestination{
    override val route = "notes"
}

val exerciseTabRowScreens = listOf(ExerciseInput, ExerciseList, Notes)