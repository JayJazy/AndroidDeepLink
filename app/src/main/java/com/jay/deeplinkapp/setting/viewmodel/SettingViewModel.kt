package com.jay.deeplinkapp.setting.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jay.deeplinkapp.setting.SettingActivity.Companion.KEY_SETTING_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _memberId = MutableStateFlow("")
    val memberId = _memberId.asStateFlow()

    init {
        savedStateHandle.get<String>(KEY_SETTING_ID)?.let {
            _memberId.value = it
        }
    }
}