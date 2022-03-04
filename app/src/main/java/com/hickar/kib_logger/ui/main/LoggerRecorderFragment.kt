package com.hickar.kib_logger.ui.main

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.hickar.kib_logger.R
import com.hickar.kib_logger.databinding.LoggerRecorderFragmentBinding

class LoggerRecorderFragment : Fragment() {
    private lateinit var viewModel: LoggerRecorderViewModel

    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscope: Sensor
    private lateinit var accelerometer: Sensor

    private var _binding: LoggerRecorderFragmentBinding? = null
    private val binding get(): LoggerRecorderFragmentBinding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val applicationContext = requireActivity().applicationContext
        val viewModelFactory = LoggerRecorderViewModelFactory(applicationContext)
        viewModel = viewModelFactory.create(LoggerRecorderViewModel::class.java)

        sensorManager = getSystemService(requireContext(), SensorManager::class.java)!!
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = LoggerRecorderFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupEventListeners()
    }

    private fun setupObservers() {
        viewModel.isRecording.observe(viewLifecycleOwner) { isRecording ->
            toggleRecordButton(isRecording)

            if (isRecording) {
                clearLogs()
                sensorManager.registerListener(accelerometerEventListener, accelerometer, SENSOR_UPDATE_RATE)
                sensorManager.registerListener(gyroscopeEventListener, gyroscope, SENSOR_UPDATE_RATE)
            } else {
                sensorManager.unregisterListener(accelerometerEventListener)
                sensorManager.unregisterListener(gyroscopeEventListener)
            }
        }

        viewModel.isFinishedRecording.observe(viewLifecycleOwner) { isFinishedRecording ->
            if (isFinishedRecording) {
                requireActivity().activityResultRegistry.register("gyro", ActivityResultContracts.CreateDocument()) { uri ->
                    if (uri == null) return@register
                    viewModel.save(uri, LoggerRecorderViewModel.GYRO_KEY)
                }.launch("gyro.csv")

                requireActivity().activityResultRegistry.register("acc", ActivityResultContracts.CreateDocument()) { uri ->
                    if (uri == null) return@register
                    viewModel.save(uri, LoggerRecorderViewModel.ACC_KEY)
                }.launch("acc.csv")
            }
        }
    }

    private fun setupEventListeners() {
        binding.recordButton.setOnClickListener {
            viewModel.toggleRecording()
        }
    }

    private fun clearLogs() {
        binding.recordLogs.text = ""
    }

    private fun toggleRecordButton(isPlaying: Boolean) {
        val recordButton = binding.recordButton
        val context = requireContext()

        val buttonIcon = if (isPlaying) R.drawable.ic_stop_24 else R.drawable.ic_play_arrow_24
        val buttonColor = if (isPlaying) R.color.red_500 else R.color.light_blue_500

        recordButton.icon = AppCompatResources.getDrawable(context, buttonIcon)
        recordButton.strokeColor = AppCompatResources.getColorStateList(context, buttonColor)
        recordButton.iconTint = AppCompatResources.getColorStateList(context, buttonColor)
    }

    private val gyroscopeEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                binding.recordLogs.append("gyro: [" +
                        "${event.values[0].round(PRECISION)}, " +
                        "${event.values[1].round(PRECISION)}, " +
                        "${event.values[2].round(PRECISION)}]\n"
                )
                viewModel.pushGyroscopeData(event.values[0], event.values[1], event.values[2])
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d("gyroscopeEventListener.onAccuracyChanged", "Gyroscope accuracy changed to $accuracy")
        }
    }

    private val accelerometerEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                Log.d("LOGGING", "STILL LOGGING ACCELEROMETER")
                binding.recordLogs.append("acc: [" +
                        "${event.values[0].round(PRECISION)}, " +
                        "${event.values[1].round(PRECISION)}, " +
                        "${event.values[2].round(PRECISION)}]\n"
                )
                viewModel.pushAccelerometerData(event.values[0], event.values[1], event.values[2])
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d("accelerometerEventListener.onAccuracyChanged", "Accelerometer accuracy changed to $accuracy")
        }
    }

    companion object {
        fun newInstance() = LoggerRecorderFragment()

        private const val SENSOR_UPDATE_RATE = 2000000 * 1000 // в миллисекундах
        private const val PRECISION = 3
    }
}