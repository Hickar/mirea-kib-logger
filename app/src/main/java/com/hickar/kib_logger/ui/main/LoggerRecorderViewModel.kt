package com.hickar.kib_logger.ui.main

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoggerRecorderViewModel(
    private val context: Context
) : ViewModel() {

    private val recorderService = RecorderService(context)
    private val data = mapOf<String, MutableList<SensorRecord>>(
        GYRO_KEY to mutableListOf(), ACC_KEY to mutableListOf()
    )

    private var _isRecording = MutableLiveData(false)
    val isRecording get() = _isRecording

    private var _isFinishedRecording = MutableLiveData(false)
    val isFinishedRecording = _isFinishedRecording

    fun toggleRecording() {
        if (_isRecording.value == true) {
            _isRecording.value = false
            _isFinishedRecording.value = true
        } else {
            _isRecording.value = true
            _isFinishedRecording.value = false
        }
    }

    fun save(uri: Uri, dataKey: String) {
        data[dataKey]?.let { recorderService.saveToDisk(uri, it) }
    }

    fun pushGyroscopeData(x: Float, y: Float, z: Float) {
        Log.d("Gyroscope", "[x: $x, y: $y, z: $z]")
        data[GYRO_KEY]?.add(SensorRecord(x, y, z))
    }

    fun pushAccelerometerData(x: Float, y: Float, z: Float) {
        Log.d("Accelerometer", "[x: $x, y: $y, z: $z]")
        data[ACC_KEY]?.add(SensorRecord(x, y, z))
    }

    companion object {
        const val GYRO_KEY = "gyro"
        const val ACC_KEY = "acc"
    }
}