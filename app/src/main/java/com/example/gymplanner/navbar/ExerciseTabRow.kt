package com.example.gymplanner.navbar

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
            .height(TAB_HEIGHT)
            .fillMaxWidth()
    ) {
        Row(Modifier.selectableGroup().fillMaxWidth(), Arrangement.SpaceEvenly) {
            allScreens.forEach { screen ->
                ExerciseTab(
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen,
                    contentDescription = "Tab: ${screen.route}"
                )
            }
        }
    }
}

@Composable
private fun ExerciseTab(
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean,
    contentDescription: String
) {
    val color = MaterialTheme.colorScheme.onSurface
    val durationMillis = if (selected) TAB_FADE_IN_DURATION else TAB_FADE_OUT_DURATION
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TAB_FADE_IN_DELAY
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = INACTIVE_TAB_OPACITY),
        animationSpec = animSpec
    )
    Row(
        modifier = Modifier
            .animateContentSize()
            .height(TAB_HEIGHT)
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
            .semantics {
                this.contentDescription = contentDescription
            }
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

private val TAB_HEIGHT = 64.dp
private const val INACTIVE_TAB_OPACITY = 0.60f
private val TAB_MINIMUM_WIDTH = 80.dp

private const val TAB_FADE_IN_DURATION = 150
private const val TAB_FADE_IN_DELAY = 100
private const val TAB_FADE_OUT_DURATION = 100
