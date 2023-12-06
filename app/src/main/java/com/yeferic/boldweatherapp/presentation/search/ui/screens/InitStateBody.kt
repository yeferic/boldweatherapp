package com.yeferic.boldweatherapp.presentation.search.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yeferic.boldweatherapp.R
import com.yeferic.boldweatherapp.core.ui.widgets.LottieAnimationWidget

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InitStateBody() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        LottieAnimationWidget(modifier = Modifier.align(Alignment.Center), id = R.raw.empty_state)
    }
}
