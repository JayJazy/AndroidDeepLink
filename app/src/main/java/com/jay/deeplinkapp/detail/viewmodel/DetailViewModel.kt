package com.jay.deeplinkapp.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jay.deeplinkapp.detail.DetailActivity.Companion.KEY_DETAIL_ID
import com.jay.deeplinkapp.util.logFunction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _detailId = MutableStateFlow("")
    val detailId = _detailId.asStateFlow()

    init {
        savedStateHandle.get<String>(KEY_DETAIL_ID)?.let {
            logFunction("detailViewModel ID : $it")
            _detailId.value = it
        }
    }
}