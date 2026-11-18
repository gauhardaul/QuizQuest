
package com.example.mobileproject.ui.topics

import androidx.lifecycle.ViewModel
import com.example.mobileproject.data.questionsFor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class QuizTopic(val name: String, val description: String) {
    val questionCount: Int = questionsFor(name).size
}

class TopicsViewModel : ViewModel() {

    private val _topics = MutableStateFlow(
        listOf(
            QuizTopic("Math", "Arithmetic and shapes"),
            QuizTopic("IT", "Computers and programming"),
            QuizTopic("Science", "Space, plants, and chemistry")
        )
    )

    val topics: StateFlow<List<QuizTopic>> = _topics
}
