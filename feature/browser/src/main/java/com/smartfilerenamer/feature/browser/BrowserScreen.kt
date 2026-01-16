package com.smartfilerenamer.feature.browser

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserScreen(
    viewModel: BrowserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val openDocumentTreeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        if (uri != null) {
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, takeFlags)
            viewModel.openDirectory(uri)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart File Renamer") },
                actions = {
                    IconButton(onClick = { openDocumentTreeLauncher.launch(null) }) {
                        Text("Open")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (uiState.currentPath == null) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No directory selected")
                    Button(onClick = { openDocumentTreeLauncher.launch(null) }) {
                        Text("Select Directory")
                    }
                }
            } else {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (uiState.error != null) {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    FileList(files = uiState.files)
                }
            }
        }
    }
}

@Composable
fun FileList(files: List<com.smartfilerenamer.core.common.model.SmartFile>) {
    LazyColumn {
        items(files) { file ->
            ListItem(
                headlineContent = { Text(file.name) },
                supportingContent = { Text(if (file.isDirectory) "Directory" else "${file.size} bytes") },
                leadingContent = {
                    Text(if (file.isDirectory) "[D]" else "[F]")
                }
            )
            HorizontalDivider()
        }
    }
}
