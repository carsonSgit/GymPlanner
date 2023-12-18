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
@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.codelabs.state

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import android.annotation.SuppressLint
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
import java.time.LocalDate
import java.util.Calendar
import java.util.Date


/*
 * NoteInput Composable: Allows users to input notes with labels, dates, and priorities.
 *
 * @param viewModel: ViewModel for managing notes data
 * @param db: FirebaseFirestore instance for interacting with Firebase Firestore
 * @param modifier: Modifier for configuring the layout of the NoteInput Composable
 */
@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInput(
    viewModel: NotesViewModel,
    db: FirebaseFirestore,
    modifier: Modifier = Modifier
) {
    // State variables to store user input values
    var noteLabel by rememberSaveable { mutableStateOf("") }
    var noteId by rememberSaveable { mutableStateOf(0) }
    var notePriority by rememberSaveable { mutableStateOf(Priority.LOW) }
    var noteDate by rememberSaveable {
        mutableStateOf("")
    }
    val placeholder: MutableList<String> by remember { mutableStateOf(mutableListOf()) }
    // Column to align input values together
    Column(
        modifier = modifier.padding(58.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text field for entering note labels
        OutlinedTextField(
            value = noteLabel,
            onValueChange = { noteLabel = it },
            label = { Text("Enter a note!") },
        )

        // Date picker component
        DatePicker { selectedDate ->

           placeholder.add(selectedDate)
        }

        placeholder.forEach{item ->
            noteDate = item

        }

        // Dropdown for selecting note priority
        PriorityDropdown(notePriority) { priority ->
            notePriority = priority
        }

        // Button to add note to the list
        Button(
            onClick = {
                if (noteLabel.isNotBlank()) {
                    val notesItem = Notes(noteId, noteLabel,noteDate ,notePriority)
                    viewModel.addNotes(notesItem)
                    // Add Note to Firestore
                    addNoteToFirestore(db, noteLabel,noteDate ,notePriority, noteId)

                    noteLabel = ""
                    noteId++
                }
            },
            modifier = Modifier
                .padding(12.dp)
                .height(48.dp)
        ) {
            Text(text = "Add Note", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

/*
 * Adds a note to the Firestore database.
 *
 * @param db: Instance of FirebaseFirestore
 * @param noteLabel: Label of the note
 * @param priority: Priority of the note
 * @param noteId: Unique identifier for the note
 */
private fun addNoteToFirestore(db: FirebaseFirestore, noteLabel: String,noteDate: String ,priority: Priority ,noteId: Int) {
    val taskItem = hashMapOf(
        "name" to noteLabel,
        "priority" to priority,
        "date" to noteDate
    )
    db.collection("tasks").document(noteId.toString())
        .set(taskItem)
}

/*
 * DatePicker Composable: Allows users to pick a date for the note.
 */
@Composable
fun DatePicker(onDateSelected: (String) -> Unit) {
    // Context and Calendar instance
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month, and day
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()




    // State variable to store selected date
    val mDate = remember { mutableStateOf("") }

    // DatePickerDialog for selecting a date
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _, year, month, dayOfMonth ->
            mDate.value = "$year-${month + 1}-$dayOfMonth"
            onDateSelected(mDate.value)
        }, mYear, mMonth, mDay
    )

    // Card containing a row with an OutlinedTextField and IconButton
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
            // OutlinedTextField to display selected date
            OutlinedTextField(
                value = mDate.value,
                onValueChange = { },
                label = { Text("Selected Date") },
                readOnly = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 4.dp)
            )

            // IconButton with Icons.Default.DateRange to show DatePickerDialog
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

/*
 * PriorityDropdown Composable: Allows users to select the priority of the note.
 *
 * @param selectedPriority: Currently selected priority
 * @param onPrioritySelected: Callback for when a priority is selected
 */
@Composable
fun PriorityDropdown(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    // State variable to track dropdown expansion
    var expanded by rememberSaveable { mutableStateOf(false) }
    val priorities = Priority.values()

    // Box containing a Text displaying the selected priority
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

    // DropdownMenu displaying available priorities
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
