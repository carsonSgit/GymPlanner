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

/**
 * Enumeration representing the priority levels for ToDo items.
 */
enum class Priority {
    /**
     * Low priority level.
     */
    LOW {
        override val text = "Low"
    },

    /**
     * Medium priority level.
     */
    MEDIUM {
        override val text = "Medium"
    },

    /**
     * High priority level.
     */
    HIGH {
        override val text = "High"
    };

    /**
     * Abstract property representing the text associated with the priority level.
     */
    abstract val text: String
}

/**
 * Class representing a ToDo item.
 *
 * @param id The unique identifier of the ToDo item.
 * @param label The text content of the ToDo item.
 * @param priority The priority level of the ToDo item.
 * @param initialChecked Initially false as the ToDo item has not been done.
 */
class Notes(
    val id: Int,
    val label: String,
    val priority: Priority,
    initialChecked: Boolean = false
) {
    /**
     * Property to hold the value of the checkbox, indicating whether the ToDo item is done or not.
     */
    var checked: Boolean by mutableStateOf(initialChecked)
}
