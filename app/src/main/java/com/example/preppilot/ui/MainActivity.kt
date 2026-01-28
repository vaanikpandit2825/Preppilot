package com.example.preppilot.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.preppilot.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUserQuestion = findViewById<EditText>(R.id.etUserQuestion)
        val btnExplain = findViewById<Button>(R.id.btnExplain)

        val tvResult = findViewById<TextView>(R.id.tvResult)
        val tvError = findViewById<TextView>(R.id.tvError)
        val errorCard = findViewById<View>(R.id.errorCard)

        btnExplain.setOnClickListener {

            val question = etUserQuestion.text.toString().trim()

            if (question.isEmpty()) {
                errorCard.visibility = View.VISIBLE
                tvError.text = "Please enter a question"
                tvResult.text = ""
                return@setOnClickListener
            }

            errorCard.visibility = View.GONE

            val answer = when {
                question.contains("stack", true) ->
                    "Stack is a linear data structure that follows LIFO (Last In First Out).\n\n" +
                            "Operations:\n• Push – add element\n• Pop – remove element\n\n" +
                            "Used in recursion, undo operations, expression evaluation."

                question.contains("queue", true) ->
                    "Queue follows FIFO (First In First Out).\n\n" +
                            "Operations:\n• Enqueue – insert\n• Dequeue – remove\n\n" +
                            "Used in scheduling, buffering."

                question.contains("tree", true) ->
                    "Tree is a hierarchical data structure.\n\n" +
                            "Key terms:\n• Root\n• Parent / Child\n• Leaf\n\n" +
                            "Used in file systems, databases."

                else ->
                    "Good question \n\nThis feature will be powered by AI soon."
            }

            tvResult.text = answer
        }

    }
}
