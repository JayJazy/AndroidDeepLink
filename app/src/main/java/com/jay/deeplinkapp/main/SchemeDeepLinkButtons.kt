package com.jay.deeplinkapp.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jay.deeplinkapp.detail.DetailActivity
import com.jay.deeplinkapp.setting.SettingActivity

@Composable
fun SchemeDeepLinkButtons(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

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
            text = "Custom Scheme",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = {
                DetailActivity.startActivity(context, "999")
            }
        ) {
            Text("Detail 화면 (직접 이동)")
        }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = {
                SettingActivity.startActivity(context, "100")
            }
        ) {
            Text("Setting 화면 (직접 이동)")
        }
    }
}