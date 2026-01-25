package com.example.preppilot.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preppilot.R
import com.example.preppilot.utils.ResultState

import com.example.preppilot.viewmodel.ConceptViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ConceptViewModel
    private lateinit var adapter: ExplanationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val spinner = findViewById<Spinner>(R.id.topicSpinner)
        val etQuestion = findViewById<EditText>(R.id.etUserQuestion)
        val button = findViewById<Button>(R.id.btnExplain)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)


        adapter = ExplanationAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val topics = listOf("Stack", "Queue")
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            topics
        )


        viewModel = ViewModelProvider(this)[ConceptViewModel::class.java]


        viewModel.state.observe(this) { result ->
            when (result) {

                is ResultState.Loading -> {
                    adapter.submit(listOf("Thinking... 🤔"))
                }

                is ResultState.Success -> {
                    adapter.submit(result.data)
                }
            }
        }


        button.setOnClickListener {

            val userText = etQuestion.text.toString().trim()

            if (userText.length >= 5) {
                viewModel.explainFromUserQuestion(userText)
                etQuestion.text.clear()
            } else {
                viewModel.explainFromTopic(
                    spinner.selectedItem.toString()
                )
            }
        }


    }
    }

