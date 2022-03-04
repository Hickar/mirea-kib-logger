package com.hickar.kib_logger.ui.main

fun Float.round(decimals: Int = 2): Float {
    return "%.${decimals}f".format(this).replace(",", ".").toFloat()
}