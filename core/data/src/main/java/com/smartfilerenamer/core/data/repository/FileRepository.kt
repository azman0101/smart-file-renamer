package com.smartfilerenamer.core.data.repository

import android.net.Uri
import com.smartfilerenamer.core.common.model.SmartFile
import com.smartfilerenamer.core.common.util.Result
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    fun getFiles(directoryUri: Uri): Flow<Result<List<SmartFile>>>
}
