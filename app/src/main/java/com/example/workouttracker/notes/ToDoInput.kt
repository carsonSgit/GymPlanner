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
@file:OptIn(ExperimentalMaterial3Api::class)

package com.codelabs.state

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp


/*
    Main components of the assignment are done here
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoInput(
    viewModel: ToDoViewModel,
    modifier: Modifier = Modifier
) {
    // all user input values...
    var toDoLabel by rememberSaveable { mutableStateOf("") }
    var toDoId by rememberSaveable { mutableStateOf(0) }
    var toDoPriority by rememberSaveable { mutableStateOf(Priority.LOW) }

    // creates a column so that you can align all input values together
    Column(
            modifier = modifier.padding(58.dp, top = 58.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        // all necessary code for user input (saves into label value)
        TextField(
            value = toDoLabel,
            onValueChange = { toDoLabel = it },
            label = { Text("Enter a task!") },
            modifier = Modifier.padding(bottom = 4.dp)
        )
        // Dropdown for priority of todo item
        PriorityDropdown(toDoPriority) { priority ->
            toDoPriority = priority
        }
        // add to todo list if not blank
        Button(
            onClick = {
                if (toDoLabel.isNotBlank()) {
                    val toDoItem = ToDo(toDoId, toDoLabel, toDoPriority)
                    viewModel.addToDoItem(toDoItem)
                    toDoLabel = ""
                    toDoId++
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .height(48.dp)
        ) {
            Text("Add To-Do")
        }
    }
}

// INSPO SOURCE:
// @see: https://alexzh.com/jetpack-compose-dropdownmenu/
// HAD TO WATCH SOME YOUTUBE VIDEOS TO FIGURE OUT THE PARAMS AS WELL
@Composable
fun PriorityDropdown(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val priorities = Priority.values()

    Box(
        modifier = Modifier
            .clickable { expanded = true }
            .border(1.dp, Color.Gray)
            .padding(16.dp)
            .semantics { contentDescription = "Select Priority" },
        ) {
        Text(
            text = "Priority: ${selectedPriority.text}"
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        priorities.forEach { priority ->
            DropdownMenuItem(
                text = { Text(text = priority.text) },
                onClick = {
                    onPrioritySelected(priority)
                    expanded = false
                }
            )
        }
    }
}

