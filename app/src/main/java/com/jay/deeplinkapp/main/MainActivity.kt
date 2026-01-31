package com.jay.deeplinkapp.main

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import com.jay.deeplinkapp.deeplink.DeepLinkInfo
import com.jay.deeplinkapp.detail.DetailActivity.Companion.KEY_DETAIL_ID
import com.jay.deeplinkapp.setting.SettingActivity.Companion.KEY_SETTING_ID
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

        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "딥 링크 연습",
                style = MaterialTheme.typography.headlineLarge
            )

            SpacerHeight(48.dp)

            Button(
                onClick = {
                    selectedDeepLink = DeepLinkInfo.DETAIL.buildDeepLink(KEY_DETAIL_ID to "999")
                    showBottomSheet = true
                },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Detail 화면")
            }

            SpacerHeight(16.dp)

            Button(
                onClick = {
                    selectedDeepLink = DeepLinkInfo.SETTING.buildDeepLink(KEY_SETTING_ID to "100")
                    showBottomSheet = true
                },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Setting 화면")
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                DeepLinkBottomSheet(
                    deepLink = selectedDeepLink,
                    onCopy = {
                        copyToClipboard(context, selectedDeepLink)
                        scope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                        }
                    },
                    onOpen = {
                        openDeepLink(context, selectedDeepLink)
                        scope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                        }
                    },
                    onDismiss = {
                        scope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                        }
                    }
                )
            }
        }
    }

    private fun openDeepLink(context: Context, deepLinkUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, deepLinkUrl.toUri())
        context.startActivity(intent)
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