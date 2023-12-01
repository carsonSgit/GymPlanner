/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codelabs.state

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun ToDoScreen(
    modifier: Modifier = Modifier,
    toDoViewModel: ToDoViewModel = viewModel()
) {
    Column(modifier = modifier) {
        ToDoInput(toDoViewModel)

        ToDoList(
            list = toDoViewModel.toDo,
            onCheckedTask = { task, checked ->
                toDoViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                toDoViewModel.remove(task)
            }
        )
    }
}
