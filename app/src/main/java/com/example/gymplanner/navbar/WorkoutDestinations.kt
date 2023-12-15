package com.example.gymplanner.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

interface WorkoutDestination {
    val route: String
    val icon: ImageVector
}

object ExerciseInput: WorkoutDestination {
    override val route = "add"
    override val icon: ImageVector = Icons.Default.Create
}
object Notes: WorkoutDestination {
    override val route = "notes"
    override val icon: ImageVector = Icons.Default.AddCircle
}
object SignIn: WorkoutDestination {
    override val route = "account"
    override val icon: ImageVector = Icons.Default.AccountCircle
}

object Calendar: WorkoutDestination {
    override val route = "calendar"
    override val icon: ImageVector = Icons.Default.DateRange
}
object About: WorkoutDestination {
    override val route = "about"
    override val icon: ImageVector = Icons.Default.Info
}

val exerciseTabRowScreens = listOf(ExerciseInput, Notes, Calendar, SignIn, About)