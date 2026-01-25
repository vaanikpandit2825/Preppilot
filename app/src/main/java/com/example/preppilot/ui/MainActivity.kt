package com.example.preppilot.ui

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
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

    private val SPEECH_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val spinner = findViewById<Spinner>(R.id.topicSpinner)
        val etQuestion = findViewById<EditText>(R.id.etUserQuestion)
        val explainButton = findViewById<Button>(R.id.btnExplain)
        val micButton = findViewById<Button>(R.id.btnMic)
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

        explainButton.setOnClickListener {
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


        micButton.setOnClickListener {
            startVoiceInput()
        }
    }


    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Speak your question"
            )
        }

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Voice input not supported",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val result =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

            if (!result.isNullOrEmpty()) {
                findViewById<EditText>(R.id.etUserQuestion)
                    .setText(result[0])
            }
        }
    }
}
