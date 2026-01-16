package com.smartfilerenamer.feature.analyzer

import android.content.Context
import android.net.Uri
import com.smartfilerenamer.core.data.repository.ContentAnalyzer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentAnalyzerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ContentAnalyzer {

    override suspend fun analyze(uri: Uri): String {
        return try {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    val sizeIndex = it.getColumnIndex(android.provider.OpenableColumns.SIZE)

                    val name = if (nameIndex != -1) it.getString(nameIndex) else "Unknown"
                    val size = if (sizeIndex != -1) it.getLong(sizeIndex) else 0L

                    "Analyzed: $name ($size bytes)"
                } else {
                    "Analysis failed: Empty cursor"
                }
            } ?: "Analysis failed: Cursor null"
        } catch (e: Exception) {
            "Analysis error: ${e.message}"
        }
    }
}
