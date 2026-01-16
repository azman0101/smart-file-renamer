package com.smartfilerenamer.core.data.repository

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.smartfilerenamer.core.common.model.SmartFile
import com.smartfilerenamer.core.common.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileRepository {

    override fun getFiles(directoryUri: Uri): Flow<Result<List<SmartFile>>> = flow {
        emit(Result.Loading)
        try {
            val documentFile = DocumentFile.fromTreeUri(context, directoryUri)
            if (documentFile == null || !documentFile.isDirectory) {
                emit(Result.Error(Exception("Invalid directory or permission issue")))
                return@flow
            }

            // Note: listFiles() can be slow on large directories.
            val files = documentFile.listFiles().map { file ->
                SmartFile(
                    uri = file.uri,
                    name = file.name ?: "Unknown",
                    isDirectory = file.isDirectory,
                    size = if (file.isDirectory) 0 else file.length(),
                    lastModified = file.lastModified(),
                    mimeType = file.type
                )
            }
            emit(Result.Success(files))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}
