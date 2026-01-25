package com.example.preppilot.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.preppilot.data.ConceptRepository
import com.example.preppilot.utils.ResultState

class ConceptViewModel : ViewModel() {

    private val repository = ConceptRepository()

    private val _state = MutableLiveData<ResultState>()
    val state: LiveData<ResultState> = _state

    fun explainFromTopic(topic: String) {
        _state.value = ResultState.Loading

        Handler(Looper.getMainLooper()).postDelayed({
            val data = repository.getExplanation(topic)
            _state.value = ResultState.Success(data)
        }, 2000)
    }

    fun explainFromUserQuestion(question: String) {
        _state.value = ResultState.Loading

        Handler(Looper.getMainLooper()).postDelayed({
            _state.value = ResultState.Success(
                listOf(
                    "You asked:",
                    question,
                    "Explanation will come here."
                )
            )
        }, 2000)
    }
}
