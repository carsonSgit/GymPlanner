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
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Composable function representing the main screen for managing ToDo items.
 *
 * @param db The instance of FirebaseFirestore for interacting with the Firebase database.
 * @param modifier Additional modifier for customization.
 * @param notesViewModel The ViewModel responsible for managing ToDo items.
 */
@Composable
fun NotesScreen(
    db: FirebaseFirestore,
    modifier: Modifier = Modifier,
    notesViewModel: NotesViewModel = viewModel()
) {
    // Main column layout for the NotesScreen
    Column(modifier = modifier) {
        // Composable for adding new ToDo items
        NoteInput(notesViewModel, db)

        // Composable for displaying the list of ToDo items
        NotesList(
            list = notesViewModel.notes,
            onCheckedTask = { task, checked ->
                notesViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                notesViewModel.remove(task)
            }
        )
    }
}
