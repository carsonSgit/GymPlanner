package com.example.workouttracker.navbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseTabRow(
    allScreens: List<WorkoutDestination>,
    onTabSelected: (WorkoutDestination) -> Unit,
    currentScreen: WorkoutDestination
) {
    Surface(
        Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(Modifier.selectableGroup().fillMaxWidth(), Arrangement.SpaceEvenly) {
            allScreens.forEach { screen ->
                ExerciseTab(
                    icon = screen.icon, // Pass the icon
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
private fun ExerciseTab(
    icon: ImageVector, // Use ImageVector for the icon
    onSelected: () -> Unit,
    selected: Boolean
) {
    val color = MaterialTheme.colorScheme.onSurface
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )
    Row(
        modifier = Modifier
            //.width(TabMinimumWidth)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            //.clearAndSetSemantics { contentDescription = "Tab: ${icon.description}" }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tabTintColor,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
        )

    }
}

private val TabHeight = 64.dp
private const val InactiveTabOpacity = 0.60f
private val TabMinimumWidth = 80.dp

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100
