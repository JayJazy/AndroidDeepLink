package com.jay.deeplinkapp.main

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.jay.deeplinkapp.base.BaseActivity
import com.jay.deeplinkapp.util.compose.SpacerHeight
import com.jay.deeplinkapp.util.copyToClipboard
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        var selectedDeepLink by remember { mutableStateOf("") }

        val onDismiss: () -> Unit = {
            scope.launch {
                sheetState.hide()
                showBottomSheet = false
            }
        }

        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SchemeDeepLinkButtons()

            SpacerHeight(48.dp)

            AppLinkButtons(
                onClick = { appLinkUrl ->
                    selectedDeepLink = appLinkUrl
                    showBottomSheet = true
                }
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                DeepLinkBottomSheet(
                    deepLink = selectedDeepLink,
                    onDismiss = onDismiss,
                    onCopy = {
                        copyToClipboard(context, selectedDeepLink)
                        onDismiss()
                    },
                    onOpen = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, selectedDeepLink.toUri()))
                        onDismiss()
                    }
                )
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            or Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
            }
        }
    }
}