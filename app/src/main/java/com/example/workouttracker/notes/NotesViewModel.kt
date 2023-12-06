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

class NotesViewModel : ViewModel() {
    private val _notes = mutableListOf<Notes>().toMutableStateList()

    val notes: List<Notes>
        get() = _notes

    fun remove(item: Notes) {
        _notes.remove(item)
    }
    fun addNotes(item: Notes) {
        _notes.add(item)
    }
    fun changeTaskChecked(item: Notes, checked: Boolean) =
        notes.find { it.id == item.id }?.let { td ->
            td.checked = checked
        }
}

