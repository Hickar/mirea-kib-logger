package com.hickar.kib_logger.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoggerRecorderViewModel : ViewModel() {
    private var _isRecording = MutableLiveData(false)
    val isRecording get() = _isRecording

    fun toggleRecording() {
        if (_isRecording.value == true) {
            _isRecording.value = false
        } else {
            _isRecording.value = true
        }
    }
}