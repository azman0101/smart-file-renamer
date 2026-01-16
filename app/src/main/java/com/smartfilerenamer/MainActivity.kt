package com.smartfilerenamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.smartfilerenamer.core.ui.theme.SmartFileRenamerTheme
import com.smartfilerenamer.feature.browser.BrowserScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartFileRenamerTheme {
                BrowserScreen()
            }
        }
    }
}
