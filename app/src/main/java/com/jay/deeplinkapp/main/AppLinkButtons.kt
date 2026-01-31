package com.jay.deeplinkapp.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jay.deeplinkapp.deeplink.DeepLinkInfo
import com.jay.deeplinkapp.detail.DetailActivity.Companion.KEY_DETAIL_ID
import com.jay.deeplinkapp.setting.SettingActivity.Companion.KEY_SETTING_ID

@Composable
fun AppLinkButtons(
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = "Custom App Link",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { onClick(DeepLinkInfo.DETAIL.buildDeepLink(KEY_DETAIL_ID to "999")) }
        ) {
            Text("Detail 화면")
        }

        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { onClick(DeepLinkInfo.SETTING.buildDeepLink(KEY_SETTING_ID to "100")) }
        ) {
            Text("Setting 화면")
        }
    }
}