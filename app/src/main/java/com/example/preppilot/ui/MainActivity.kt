package com.example.preppilot.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preppilot.R
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ExplanationAdapter
    private var selectedTopic = "Stack"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chipStack = findViewById<Chip>(R.id.chipStack)
        val chipQueue = findViewById<Chip>(R.id.chipQueue)
        val chipTree = findViewById<Chip>(R.id.chipTree)
        val btnExplain = findViewById<Button>(R.id.btnExplain)
        val etQuestion = findViewById<EditText>(R.id.etUserQuestion)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = ExplanationAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fun selectChip(chip: Chip, topic: String) {
            selectedTopic = topic
            listOf(chipStack, chipQueue, chipTree).forEach {
                it.setChipBackgroundColorResource(R.color.card_bg)
                it.setTextColor(getColor(R.color.text_primary))
            }
            chip.setChipBackgroundColorResource(R.color.accent)
            chip.setTextColor(getColor(android.R.color.white))
        }

        chipStack.setOnClickListener { selectChip(chipStack, "Stack") }
        chipQueue.setOnClickListener { selectChip(chipQueue, "Queue") }
        chipTree.setOnClickListener { selectChip(chipTree, "Tree") }

        chipStack.performClick()

        btnExplain.setOnClickListener {
            val text = etQuestion.text.toString().ifEmpty { selectedTopic }
            adapter.submit(listOf("Topic: $selectedTopic", "Question: $text"))
        }
    }
}
