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

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Composable function to display a list of ToDo items.
 *
 * @param list List of ToDo items to be displayed.
 * @param onCheckedTask Callback for handling changes in the completion status of a ToDo item.
 * @param onCloseTask Callback for handling the closure of a ToDo item.
 * @param modifier Additional modifier for customization.
 */
@Composable
fun NotesList(
    list: List<Notes>,
    onCheckedTask: (Notes, Boolean) -> Unit,
    onCloseTask: (Notes) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            // Display individual ToDo items using the NoteItem composable
            NoteItem(
                taskName = task.label,
                priority = task.priority,
                checked = task.checked,
                onCheckedChange = { checked -> onCheckedTask(task, checked) },
                onClose = { onCloseTask(task) }
            )
        }
    }
}