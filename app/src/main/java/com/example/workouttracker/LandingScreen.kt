package com.example.workouttracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

@Composable
fun LandingScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier){
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        val currentOnTimeout by rememberUpdatedState(onTimeout)

        LaunchedEffect(Unit){
            delay(2000)
            currentOnTimeout()
        }
        Image(painterResource(id = R.drawable.logo), contentDescription = null)
    }
}