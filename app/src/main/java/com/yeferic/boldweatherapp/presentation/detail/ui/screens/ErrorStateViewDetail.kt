package com.yeferic.boldweatherapp.presentation.detail.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yeferic.boldweatherapp.R
import com.yeferic.boldweatherapp.core.ui.theme.DarkHolo
import com.yeferic.boldweatherapp.core.ui.widgets.LottieAnimationWidget

@Composable
fun ErrorStateViewDetail() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimationWidget(
            modifier = Modifier.size(72.dp),
            id = com.yeferic.boldweatherapp.R.raw.error,
        )
        Text(
            text = stringResource(id = R.string.item_not_found),
            fontWeight = FontWeight.Light,
            color = DarkHolo,
            textAlign = TextAlign.Center,
        )
    }
}
