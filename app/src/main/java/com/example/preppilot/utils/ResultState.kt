package com.example.preppilot.utils

sealed class ResultState{
    object Loading : ResultState()

    data class Success(
        val data: List<String>
    ): ResultState()
}