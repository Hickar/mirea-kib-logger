package com.hickar.kib_logger.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.hickar.kib_logger.R
import com.hickar.kib_logger.databinding.LoggerRecorderFragmentBinding
import java.util.logging.Logger

class LoggerRecorderFragment : Fragment() {

    private var _binding: LoggerRecorderFragmentBinding? = null
    private val binding get(): LoggerRecorderFragmentBinding = _binding!!

    private lateinit var viewModel: LoggerRecorderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoggerRecorderViewModel::class.java)
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
        viewModel.isRecording.observe(viewLifecycleOwner) { isPlaying ->
            val context = requireContext()
            val buttonIcon = if (isPlaying) R.drawable.ic_stop_24 else R.drawable.ic_play_arrow_24
            val buttonColor = if (isPlaying) R.color.red_500 else R.color.light_blue_500

            binding.recordButton.icon = AppCompatResources.getDrawable(context, buttonIcon)
            binding.recordButton.strokeColor = AppCompatResources.getColorStateList(context, buttonColor)
            binding.recordButton.iconTint = AppCompatResources.getColorStateList(context, buttonColor)
        }
    }

    private fun setupEventListeners() {
        binding.recordButton.setOnClickListener {
            viewModel.toggleRecording()
        }
    }

    companion object {
        fun newInstance() = LoggerRecorderFragment()
    }
}