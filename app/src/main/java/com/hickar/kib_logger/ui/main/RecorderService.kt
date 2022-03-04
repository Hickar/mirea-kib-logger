package com.hickar.kib_logger.ui.main

import android.content.Context
import android.net.Uri
import com.fasterxml.jackson.dataformat.csv.CsvMapper

class RecorderService(
    private val applicationContext: Context
) {
    private val csvMapper = CsvMapper()

    fun saveToDisk(uri: Uri, payload: List<SensorRecord>) {
        val schema = csvMapper.typedSchemaFor(SensorRecord::class.java).withHeader()
        val csvWriter = csvMapper.writer(schema)
        val file = applicationContext.contentResolver.openOutputStream(uri)

        val fileWriter = csvWriter.writeValues(file)
        fileWriter.writeAll(payload)
        fileWriter.close()
    }
}