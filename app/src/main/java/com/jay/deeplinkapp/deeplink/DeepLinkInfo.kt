package com.jay.deeplinkapp.deeplink

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import com.jay.deeplinkapp.main.MainActivity
import com.jay.deeplinkapp.R
import com.jay.deeplinkapp.detail.DetailActivity
import com.jay.deeplinkapp.setting.SettingActivity

enum class DeepLinkInfo(@StringRes val hostResId: Int) : DeepLinkBuilder, DeepLinkHandler {
    MAIN(R.string.scheme_host_main) {
        override fun createIntentForDeepLink(context: Context, deepLinkUri: Uri): Intent =
            MainActivity.Companion.getIntent(context)

        override fun buildDeepLink(vararg params: Pair<String, String>): String {
            return "${APP_LINK_BASE_URL}/main"
        }
    },

    DETAIL(R.string.scheme_host_detail) {
        override fun createIntentForDeepLink(context: Context, deepLinkUri: Uri): Intent =
            DetailActivity.getIntentFromUri(context, deepLinkUri)

        override fun buildDeepLink(vararg params: Pair<String, String>): String {
            val query = params.joinToString("&") { "${it.first}=${it.second}" }
            return "${APP_LINK_BASE_URL}/detail?$query"
        }
    },

    SETTING(R.string.scheme_host_setting) {
        override fun createIntentForDeepLink(context: Context, deepLinkUri: Uri): Intent =
            SettingActivity.getIntentFromUri(context, deepLinkUri)

        override fun buildDeepLink(vararg params: Pair<String, String>): String {
            val query = params.joinToString("&") { "${it.first}=${it.second}" }
            return "${APP_LINK_BASE_URL}/setting?$query"
        }
    };

    private fun getHost(context: Context): String = context.getString(hostResId)

    companion object {
        private const val APP_LINK_BASE_URL = "https://jayjazy.github.io"

        /**
         * URI를 분석하여 적절한 DeepLinkInfo를 반환
         *
         * 매칭 우선순위:
         * 1. Host 매칭: jay://detail → DETAIL
         * 2. 첫 Path 매칭: jay://app/detail → DETAIL
         * 3. 루트 경로: jay://app/ → MAIN
         * 4. 실패 시: 기본값으로 MAIN 반환
         *
         * @param context Android Context
         * @param uri 분석할 URI
         * @return [DeepLinkInfo]
         */
        operator fun invoke(context: Context, uri: Uri): DeepLinkInfo {
            val host = uri.host
            val firstPathSegment = uri.pathSegments.firstOrNull()

            val matched = entries.find {
                val expectedHost = it.getHost(context)
                host == expectedHost || firstPathSegment == expectedHost
            }

            return when {
                matched != null -> matched
                uri.path == "/" -> MAIN
                else -> MAIN
            }
        }

        fun createMainActivityIntent(context: Context) = MainActivity.Companion.getIntent(context)

        fun isSupportedUri(context: Context, uri: Uri): Boolean {
            val appScheme = context.getString(R.string.app_scheme)

            val supportedDomains = listOf(
                context.getString(R.string.app_link_domain_primary),
                context.getString(R.string.app_link_domain_secondary)
            )

            return when {
                // 앱 전용 스킴은 항상 허용
                uri.scheme == appScheme -> true

                // 웹 링크는 설정된 도메인 허용
                uri.host in supportedDomains -> true

                // 그 외는 모두 차단
                else -> false
            }
        }
    }
}