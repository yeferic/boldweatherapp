package com.yeferic.boldweatherapp.presentation.detail.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yeferic.boldweatherapp.R
import com.yeferic.boldweatherapp.core.ui.widgets.LottieAnimationWidget

@Composable
fun LoadingStateViewDetail() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        LottieAnimationWidget(modifier = Modifier.align(Alignment.Center), id = R.raw.loading)
    }
}
