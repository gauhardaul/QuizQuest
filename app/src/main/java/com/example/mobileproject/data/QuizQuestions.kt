package com.example.mobileproject.data

private val mathQuestions = listOf(
    Question("What is 2 + 2?", listOf("3", "4", "5", "6"), "4"),
    Question("What is 7 × 3?", listOf("18", "20", "21", "24"), "21"),
    Question("How many sides does a hexagon have?", listOf("5", "6", "7", "8"), "6")
)

private val itQuestions = listOf(
    Question("How many bits are in a byte?", listOf("2", "4", "8", "16"), "8"),
    Question("Which device is used to type text?", listOf("Monitor", "Keyboard", "Speaker", "Printer"), "Keyboard"),
    Question("Which language is used in this app?", listOf("Kotlin", "HTML", "Python", "SQL"), "Kotlin")
)

private val scienceQuestions = listOf(
    Question("Which planet is closest to the Sun?", listOf("Earth", "Mars", "Venus", "Mercury"), "Mercury"),
    Question("What gas do plants absorb during photosynthesis?", listOf("Oxygen", "Helium", "Carbon dioxide", "Hydrogen"), "Carbon dioxide"),
    Question("What is the chemical formula for water?", listOf("CO2", "H2O", "O2", "NaCl"), "H2O")
)

fun questionsFor(topic: String): List<Question> = when (topic) {
    "Math" -> mathQuestions
    "IT" -> itQuestions
    "Science" -> scienceQuestions
    else -> emptyList()
}
