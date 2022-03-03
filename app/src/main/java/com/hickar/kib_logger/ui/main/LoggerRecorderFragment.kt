package com.hickar.kib_logger.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hickar.kib_logger.R

class LoggerRecorderFragment : Fragment() {

    companion object {
        fun newInstance() = LoggerRecorderFragment()
    }

    private lateinit var viewModel: LoggerRecorderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoggerRecorderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}