package com.smartfilerenamer.core.data.repository

import android.net.Uri

interface ContentAnalyzer {
    suspend fun analyze(uri: Uri): String
}
