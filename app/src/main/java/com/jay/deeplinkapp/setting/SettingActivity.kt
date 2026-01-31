package com.jay.deeplinkapp.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jay.deeplinkapp.base.BaseActivity
import com.jay.deeplinkapp.setting.viewmodel.SettingViewModel
import com.jay.deeplinkapp.util.logFunction

class SettingActivity : BaseActivity() {

    private val viewModel: SettingViewModel by viewModels()

    override fun setSavedInstanceState(savedInstanceState: Bundle?) {
        extractDeepLinkParameter(KEY_SETTING_ID)
            ?: getBundle(savedInstanceState)?.getString(KEY_SETTING_ID)
            ?: run {
                logFunction("KEY_MEMBER_ID 파라미터가 없음")
                finish()
                return
            }
    }

    @Composable
    override fun Content() {
        val memberId by viewModel.memberId.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Member ID : $memberId",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }

    companion object {

        const val KEY_SETTING_ID = "SETTING_ID"

        fun startActivity(context: Context, settingID: String) {
            context.startActivity(getIntent(context, settingID))
        }

        private fun getIntent(context: Context, settingID: String) =
            Intent(context, SettingActivity::class.java).apply {
                putExtra(KEY_SETTING_ID, settingID)
            }

        fun getIntentFromUri(context: Context, deepLink: Uri): Intent {
            val settingID = deepLink.getQueryParameter(KEY_SETTING_ID) ?: run {
                logFunction("딥링크에 SETTING_ID 파라미터 없음")
                ""
            }
            return getIntent(context, settingID)
        }
    }
}