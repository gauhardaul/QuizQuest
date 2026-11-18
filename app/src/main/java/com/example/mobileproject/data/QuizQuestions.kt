package com.example.mobileproject.data

private val mathQuestions = listOf(
    Question("What is 2 + 2?", listOf("3", "4", "5", "6"), "4"),
    Question("What is 7 × 3?", listOf("18", "20", "21", "24"), "21"),
    Question("How many sides does a hexagon have?", listOf("5", "6", "7", "8"), "6")
)

fun questionsFor(topic: String): List<Question> = when (topic) {
    "Math" -> mathQuestions
    else -> emptyList()
}
