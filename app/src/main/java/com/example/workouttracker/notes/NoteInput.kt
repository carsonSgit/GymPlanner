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

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Date


/*
    Main components of the assignment are done here
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInput(
    viewModel: NotesViewModel,
    db: FirebaseFirestore,
    modifier: Modifier = Modifier
) {
    // all user input values...
    var noteLabel by rememberSaveable { mutableStateOf("") }
    var noteId by rememberSaveable { mutableStateOf(0) }
    var notePriority by rememberSaveable { mutableStateOf(Priority.LOW) }

    // creates a column so that you can align all input values together
    Column(
            modifier = modifier.padding(58.dp, top = 58.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        // all necessary code for user input (saves into label value)
        OutlinedTextField(
            value = noteLabel,
            onValueChange = { noteLabel = it },
            label = { Text("Enter a note!") },
            modifier = Modifier.padding(bottom = 12.dp)
        )

        DatePicker()

        // Dropdown for priority of notes item
        PriorityDropdown(notePriority) { priority ->
            notePriority = priority
        }
        // add to notes list if not blank
        Button(
            onClick = {
                if (noteLabel.isNotBlank()) {
                    val notesItem = Notes(noteId, noteLabel, notePriority)
                    viewModel.addNotes(notesItem)
                    // adding the note to the Firestore database
                    val taskItem = hashMapOf(
                        "name" to noteLabel,
                        "priority" to notePriority
                    )

                    db.collection("tasks").document(noteId.toString())
                        .set(taskItem)
                    noteLabel = ""
                    noteId++


                }
            },
            modifier = Modifier
                .padding(12.dp)
                .height(48.dp)
        ) {
            Text(text = "Add Note",
                color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun DatePicker() {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month, and day
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month, and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _, year, month, dayOfMonth ->
            mDate.value = "$dayOfMonth/${month + 1}/$year"
        }, mYear, mMonth, mDay
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .fillMaxHeight(0.17f)
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
    ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = mDate.value,
                onValueChange = { },
                label = { Text("Selected Date") },
                readOnly = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 4.dp)
            )
            // IconButton with Icons.Default.DateRange
            IconButton(
                modifier = Modifier.padding(start = 12.dp),
                onClick = {
                    mDatePickerDialog.show()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null, // Content description for accessibility
                    modifier = Modifier
                        .size(32.dp)
                )
            }
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
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
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

