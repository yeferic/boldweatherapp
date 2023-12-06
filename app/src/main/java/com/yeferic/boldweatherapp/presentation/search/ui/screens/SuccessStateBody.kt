package com.yeferic.boldweatherapp.presentation.search.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yeferic.boldweatherapp.R
import com.yeferic.boldweatherapp.core.commons.Routes
import com.yeferic.boldweatherapp.core.ui.theme.DarkHolo
import com.yeferic.boldweatherapp.core.ui.theme.Holo
import com.yeferic.boldweatherapp.core.ui.theme.LightHolo
import com.yeferic.boldweatherapp.core.ui.widgets.AnimatedVisibilityWidget
import com.yeferic.boldweatherapp.core.ui.widgets.LottieAnimationWidget
import com.yeferic.boldweatherapp.core.ui.widgets.OnBottomScroll
import com.yeferic.boldweatherapp.domain.models.ItemResult
import com.yeferic.boldweatherapp.presentation.search.viewmodel.SearchViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SuccessStateBody(viewModel: SearchViewModel, navController: NavHostController) {
    val result: List<ItemResult> by viewModel.itemsResult.collectAsState(arrayListOf())
    val loadMore: Boolean by viewModel.loadingMoreItems.observeAsState(false)

    AnimatedVisibilityWidget {
        if (result.isEmpty()) {
            NotFoundResultsScaffoldBody()
        } else {
            Column(Modifier.padding(top = 64.dp)) {
                val modifierList = Modifier.weight(1f)
                Divider(
                    Modifier
                        .height(0.5.dp)
                        .background(LightHolo),
                )
                ResultSearchListView(
                    modifier = modifierList,
                    list = result,
                    onItemSelected = {
                        navController.navigate("${Routes.DETAIL_SCREEN}${it.name}")
                    },
                    loadMore = {
                        if (loadMore.not()) viewModel.loadMoreItems()
                    },
                )
                if (loadMore) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .weight(0.1f),
                    ) {
                        LottieAnimationWidget(
                            modifier = Modifier.align(Alignment.Center),
                            id = R.raw.loading_scroll,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NotFoundResultsScaffoldBody() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Holo),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LottieAnimationWidget(modifier = Modifier.size(128.dp), id = R.raw.not_found)
    }
}

@Composable
private fun ResultSearchListView(
    modifier: Modifier,
    list: List<ItemResult>,
    onItemSelected: (ItemResult) -> Unit,
    loadMore: () -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        items(list) {
            ResultItemWidget(item = it, onItemSelected)
            Divider(
                Modifier
                    .height(0.5.dp)
                    .background(LightHolo),
            )
        }
    }

    listState.OnBottomScroll {
        loadMore()
    }
}

@Composable
private fun ResultItemWidget(item: ItemResult, onClickAction: (ItemResult) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable {
                onClickAction(item)
            },
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 8.dp),
        ) {
            Text(
                text = item.name,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                color = Color.Black,
            )
            Text(
                text = item.country,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                color = DarkHolo,
            )
        }
    }
}
