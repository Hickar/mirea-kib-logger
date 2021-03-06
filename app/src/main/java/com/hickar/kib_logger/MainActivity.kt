package com.hickar.kib_logger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hickar.kib_logger.ui.main.LoggerRecorderFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoggerRecorderFragment.newInstance())
                .commitNow()
        }
    }
}