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

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

/**
 * ViewModel class responsible for managing ToDo items.
 */
class NotesViewModel : ViewModel() {
    // Mutable state list to hold the ToDo items
    private val _notes = mutableListOf<Notes>().toMutableStateList()

    /**
     * Immutable property exposing the list of ToDo items.
     */
    val notes: List<Notes>
        get() = _notes

    /**
     * Removes a ToDo item from the list.
     *
     * @param item The ToDo item to be removed.
     */
    fun remove(item: Notes) {
        _notes.remove(item)
    }

    /**
     * Adds a new ToDo item to the list.
     *
     * @param item The ToDo item to be added.
     */
    fun addNotes(item: Notes) {
        _notes.add(item)
    }

    /**
     * Changes the completion status of a ToDo item.
     *
     * @param item The ToDo item for which the completion status is to be changed.
     * @param checked The new completion status.
     */
    fun changeTaskChecked(item: Notes, checked: Boolean) =
        // Find the ToDo item with the given ID and update its completion status
        notes.find { it.id == item.id }?.let { td ->
            td.checked = checked
        }
}
