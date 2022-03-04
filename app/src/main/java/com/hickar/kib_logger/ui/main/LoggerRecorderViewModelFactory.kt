package com.hickar.kib_logger.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoggerRecorderViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoggerRecorderViewModel::class.java)) {
            return LoggerRecorderViewModel(context) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}