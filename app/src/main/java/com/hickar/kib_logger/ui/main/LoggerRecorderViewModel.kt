package com.hickar.kib_logger.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoggerRecorderViewModel : ViewModel() {
    private val recorderService = RecorderService()

    private var _isRecording = MutableLiveData(false)
    val isRecording get() = _isRecording

    fun toggleRecording() {
        _isRecording.value = _isRecording.value != true
    }

    fun pushGyroscopeData(x: Float, y: Float, z: Float) {
        Log.d("Gyroscope", "[x: $x, y: $y, z: $z]")
    }

    fun pushAccelerometerData(x: Float, y: Float, z: Float) {
        Log.d("Accelerometer", "[x: $x, y: $y, z: $z]")
    }
}