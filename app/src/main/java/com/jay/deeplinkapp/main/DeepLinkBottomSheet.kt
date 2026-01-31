package com.jay.deeplinkapp.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jay.deeplinkapp.util.compose.SpacerHeight

@Composable
fun DeepLinkBottomSheet(
    deepLink: String,
    onDismiss: () -> Unit,
    onCopy: () -> Unit,
    onOpen: () -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "딥링크 : $deepLink",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Companion.Bold
        )

        SpacerHeight(24.dp)

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onCopy,
                modifier = Modifier.Companion.weight(1f)
            ) {
                Text("복사")
            }

            Button(
                onClick = onOpen,
                modifier = Modifier.Companion.weight(1f)
            ) {
                Text("열기")
            }
        }

        SpacerHeight(8.dp)

        TextButton(
            onClick = onDismiss,
            modifier = Modifier.Companion.fillMaxWidth()
        ) {
            Text("닫기")
        }

        SpacerHeight(16.dp)
    }
}
