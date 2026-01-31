package com.jay.deeplinkapp.deeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.TaskStackBuilder
import com.jay.deeplinkapp.main.MainActivity
import com.jay.deeplinkapp.util.logFunction

class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeepLink()
    }

    private fun handleDeepLink() {
        val deepLinkUri = intent.data

        if (deepLinkUri == null) {
            logFunction("딥링크 URI가 없음")
            startMainActivity()
            return
        }

        if (!DeepLinkInfo.isSupportedUri(this, deepLinkUri)) {
            logFunction("잘못된 형식 URI: $deepLinkUri")
            startMainActivity()
            return
        }

        val deepLinkIntent = handleDeepLinkIntent(deepLinkUri)

        if (isTaskRoot) {
            TaskStackBuilder.create(this).apply {
                if (deepLinkIntent.component?.className != MainActivity::class.java.name) {
                    addNextIntentWithParentStack(DeepLinkInfo.createMainActivityIntent(this@DeepLinkActivity))
                }
                addNextIntent(deepLinkIntent)
            }.startActivities()
        } else {
            startActivity(deepLinkIntent)
        }

        finish()
    }

    private fun handleDeepLinkIntent(uri: Uri): Intent {
        val deepLinkInfo = DeepLinkInfo.invoke(this, uri)
        return deepLinkInfo.createIntentForDeepLink(this, uri)
    }

    private fun startMainActivity() {
        startActivity(DeepLinkInfo.createMainActivityIntent(this))
        finish()
    }
}