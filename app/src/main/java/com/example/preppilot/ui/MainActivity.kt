package com.example.preppilot.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preppilot.R
import com.example.preppilot.ai.GeminiClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ExplanationAdapter
    private val geminiClient = GeminiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUserQuestion = findViewById<EditText>(R.id.etUserQuestion)
        val btnExplain = findViewById<Button>(R.id.btnExplain)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = ExplanationAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnExplain.setOnClickListener {

            val question = etUserQuestion.text.toString().trim()

            if (question.isEmpty()) {
                adapter.submit(listOf("Please enter a question"))
                return@setOnClickListener
            }

            adapter.submit(listOf("Thinking... 🤔"))

            lifecycleScope.launch {
                try {
                    val result = geminiClient.explain(question)
                    adapter.submit(result)
                } catch (e: Exception) {
                    adapter.submit(listOf("Error", e.message ?: "Something went wrong"))
                }
            }
        }
    }
}
