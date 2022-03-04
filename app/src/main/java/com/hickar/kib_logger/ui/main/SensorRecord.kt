package com.hickar.kib_logger.ui.main

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.Instant

data class SensorRecord(
    val x: Float,
    val y: Float,
    val z: Float,
    val timestamp: Long = Instant.now().toEpochMilli()
)