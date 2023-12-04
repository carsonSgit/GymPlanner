package com.example.workouttracker

import android.media.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.codelabs.state.ToDoScreen
import com.example.workouttracker.exerciseinput.WorkoutInput
import com.example.workouttracker.exerciselist.WorkoutList

interface WorkoutDestination {
    val route: String
    val icon: ImageVector
}

object ExerciseInput:WorkoutDestination{
    override val route = "add"
    override val icon: ImageVector = Icons.Default.Create
}
object ExerciseList: WorkoutDestination{
    override val route = "list"
    override val icon: ImageVector = Icons.Default.Info
}
object Notes: WorkoutDestination{
    override val route = "notes"
    override val icon: ImageVector = Icons.Default.AddCircle
}
object SignIn: WorkoutDestination{
    override val route = "account"
    override val icon: ImageVector = Icons.Default.AccountCircle
}

object Calendar: WorkoutDestination{
    override val route = "calendar"
    override val icon: ImageVector = Icons.Default.DateRange
}

val exerciseTabRowScreens = listOf(ExerciseInput, ExerciseList, Notes, Calendar, SignIn)