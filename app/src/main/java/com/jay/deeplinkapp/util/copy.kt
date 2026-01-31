package com.jay.deeplinkapp.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE

fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("DeepLink", text)
    clipboard.setPrimaryClip(clip)
}