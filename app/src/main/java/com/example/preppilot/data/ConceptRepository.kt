package com.example.preppilot.data

class ConceptRepository {

    fun getExplanation(topic: String): List<String> {
        return when (topic) {

            "Stack" -> listOf(
                "Stack follows LIFO (Last In First Out)",
                "Think of plates stacked on top of each other",
                "Push adds an element to the top",
                "Pop removes the top element"
            )

            "Queue" -> listOf(
                "Queue follows FIFO (First In First Out)",
                "Think of people standing in a line",
                "Enqueue adds an element to the rear",
                "Dequeue removes an element from the front"
            )

            else -> listOf(
                "No explanation available for this topic"
            )
        }
    }
}
