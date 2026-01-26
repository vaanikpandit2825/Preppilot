package com.example.preppilot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.preppilot.data.ConceptRepository
import com.example.preppilot.utils.ResultState

class ConceptViewModel : ViewModel() {

    private val repository = ConceptRepository()

    private val _state = MutableLiveData<ResultState<List<String>>>()
    val state: LiveData<ResultState<List<String>>> = _state

    fun explainFromTopic(topic: String) {
        _state.value = ResultState.Loading
        _state.value = ResultState.Success(repository.getExplanation(topic))
    }

    fun explainFromUserQuestion(question: String) {
        _state.value = ResultState.Loading
        _state.value = ResultState.Success(
            listOf(
                "You asked: $question",
                "This will be answered using AI later.",
                "For now, showing structured explanation."
            )
        )
    }
}
