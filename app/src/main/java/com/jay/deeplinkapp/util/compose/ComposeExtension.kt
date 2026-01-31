package com.jay.deeplinkapp.util.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerHeight(dp: Dp) = Spacer(modifier = Modifier.height(dp))

@Composable
fun SpacerWidth(dp: Dp) = Spacer(modifier = Modifier.width(dp))