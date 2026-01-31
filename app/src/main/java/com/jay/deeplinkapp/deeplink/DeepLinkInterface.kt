package com.jay.deeplinkapp.deeplink

import android.content.Context
import android.content.Intent
import android.net.Uri

interface DeepLinkBuilder {
    fun buildDeepLink(vararg params: Pair<String, String>): String
}

interface DeepLinkHandler {
    fun createIntentForDeepLink(context: Context, deepLinkUri: Uri): Intent
}
