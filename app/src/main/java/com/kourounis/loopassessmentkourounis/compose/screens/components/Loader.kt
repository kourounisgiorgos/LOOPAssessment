package com.kourounis.loopassessmentkourounis.compose.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier

@Composable
fun Loader(loading: Boolean, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    if (loading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = BiasAlignment(0f, -0.4f)
        ) {
            CircularProgressIndicator()
        }
    } else {
        content.invoke()
    }
}