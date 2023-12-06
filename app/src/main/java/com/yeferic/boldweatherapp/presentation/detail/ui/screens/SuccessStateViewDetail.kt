package com.yeferic.boldweatherapp.presentation.detail.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yeferic.boldweatherapp.R
import com.yeferic.boldweatherapp.core.ui.theme.DarkHolo
import com.yeferic.boldweatherapp.domain.models.Condition
import com.yeferic.boldweatherapp.domain.models.Forecastday
import com.yeferic.boldweatherapp.domain.models.ItemDetail

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuccessStateViewDetail(itemDetail: ItemDetail) {
    LazyColumn(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 56.dp, start = 8.dp, end = 8.dp),

    ) {
        item {
            Text(
                text = "${itemDetail.current.getTemperature()} ${itemDetail.current.condition.text}",
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
            )

            ImageComponent(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .animateItemPlacement(),
                condition = itemDetail.current.condition,
            )

            Text(
                text = stringResource(id = R.string.next_days),
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(itemDetail.forecast.forecastday) {
            ItemDay(it)
        }
    }
}

@Composable
fun ImageComponent(modifier: Modifier, condition: Condition) {
    Box(
        modifier = modifier
            .height(256.dp)
            .background(Color.White),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(condition.getUrlIcon())
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.image_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
    }
}

@Composable
fun ItemDay(forecastday: Forecastday) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = forecastday.date,
                modifier = Modifier.weight(1f),
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
            )

            Text(
                text = forecastday.day.getTemperatureAvg(),
                modifier = Modifier.weight(1f),
                color = DarkHolo,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
            )
            ImageComponent(
                modifier = Modifier
                    .size(48.dp),
                condition = forecastday.day.condition,
            )
        }
        Spacer(Modifier.height(12.dp))
        Divider(Modifier.height(1.dp))
    }
}
