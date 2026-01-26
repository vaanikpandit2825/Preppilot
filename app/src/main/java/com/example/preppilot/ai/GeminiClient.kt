package com.example.preppilot.ai

import com.example.preppilot.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

class GeminiClient {

    private val model = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    suspend fun explain(question: String): List<String> {
        val prompt = """
            Explain the following DSA concept in 4–6 clear bullet points.
            Beginner friendly.
            Question: $question
        """.trimIndent()

        val response = model.generateContent(prompt)
        val text = response.text ?: return listOf("No response")

        return text
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }
}
