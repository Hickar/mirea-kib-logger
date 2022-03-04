package com.hickar.kib_logger.ui.main

fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()