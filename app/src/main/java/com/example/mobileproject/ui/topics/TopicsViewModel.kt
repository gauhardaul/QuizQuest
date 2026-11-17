
package com.example.mobileproject.ui.topics

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TopicsViewModel : ViewModel() {

    private val _topics = MutableStateFlow(
        listOf("Math", "IT", "Science")
    )

    val topics: StateFlow<List<String>> = _topics
}
