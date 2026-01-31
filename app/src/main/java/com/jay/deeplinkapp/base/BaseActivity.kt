package com.jay.deeplinkapp.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

abstract class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSavedInstanceState(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    protected abstract fun Content()

    protected fun getBundle(savedInstanceState: Bundle?): Bundle? =
        savedInstanceState ?: intent.extras

    protected open fun setSavedInstanceState(savedInstanceState: Bundle?) { }

    // 딥링크 처리를 위한 헬퍼 메서드들
    protected fun extractDeepLinkParameter(key: String): String? {
        return intent.data?.getQueryParameter(key) ?: intent.getStringExtra(key)
    }
}