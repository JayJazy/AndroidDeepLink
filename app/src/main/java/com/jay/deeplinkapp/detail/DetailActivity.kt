package com.jay.deeplinkapp.detail

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
import com.jay.deeplinkapp.detail.viewmodel.DetailViewModel
import com.jay.deeplinkapp.util.logFunction

class DetailActivity : BaseActivity() {

    private val viewModel: DetailViewModel by viewModels()

    override fun setSavedInstanceState(savedInstanceState: Bundle?) {
        val bundle = getBundle(savedInstanceState)

        extractDeepLinkParameter(KEY_DETAIL_ID)
            ?: bundle?.getString(KEY_DETAIL_ID)
            ?: run {
                logFunction("KEY_MEMBER_ID 파라미터가 없음")
                finish()
                return
            }
    }

    @Composable
    override fun Content() {
        val detailId by viewModel.detailId.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Detail ID: $detailId",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }

    companion object {
        const val KEY_DETAIL_ID = "DETAIL_ID"

        fun startActivity(context: Context, detailId: String) {
            context.startActivity(getIntent(context, detailId))
        }

        private fun getIntent(context: Context, detailId: String) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_DETAIL_ID, detailId)
            }

        fun getIntentFromUri(context: Context, deepLink: Uri): Intent {
            val detailId = deepLink.getQueryParameter(KEY_DETAIL_ID) ?: run {
                logFunction("딥링크에 DETAIL_ID 파라미터 없음")
                ""
            }
            return getIntent(context, detailId)
        }
    }
}