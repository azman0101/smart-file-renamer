package com.smartfilerenamer.core.common.model

import android.net.Uri

data class SmartFile(
    val uri: Uri,
    val name: String,
    val isDirectory: Boolean,
    val size: Long,
    val lastModified: Long,
    val mimeType: String? = null
)
