package com.hickar.kib_logger.ui.main

import java.time.Instant

data class SensorRecord(
    val x: Float,
    val y: Float,
    val z: Float,
    var timestamp: Long = Instant.now().toEpochMilli(),
)