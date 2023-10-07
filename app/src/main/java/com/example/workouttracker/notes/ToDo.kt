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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class Priority {
    LOW {
        override val text = "Low"
    },
    MEDIUM {
        override val text = "Medium"
    },
    HIGH {
        override val text = "High"
    };

    abstract val text: String
}
/**
 * ToDo class.
 * @param id: the id of the new ToDo item
 * @param label: The text content of the ToDo item
 * @param initialChecked: initially false as the ToDo item has not been done
 */
class ToDo(
    val id: Int,
    val label: String,
    val priority: Priority,
    initialChecked: Boolean = false
) {
    // a value to hold the value of the checkbox
    var checked: Boolean by mutableStateOf(initialChecked)
}
