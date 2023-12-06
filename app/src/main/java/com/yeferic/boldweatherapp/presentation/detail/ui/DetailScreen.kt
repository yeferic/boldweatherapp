package com.yeferic.boldweatherapp.presentation.detail.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.yeferic.boldweatherapp.R
import com.yeferic.boldweatherapp.domain.models.ItemDetail
import com.yeferic.boldweatherapp.presentation.detail.states.DetailUIState
import com.yeferic.boldweatherapp.presentation.detail.ui.screens.ErrorStateViewDetail
import com.yeferic.boldweatherapp.presentation.detail.ui.screens.InitStateViewDetail
import com.yeferic.boldweatherapp.presentation.detail.ui.screens.LoadingStateViewDetail
import com.yeferic.boldweatherapp.presentation.detail.ui.screens.SuccessStateViewDetail
import com.yeferic.boldweatherapp.presentation.detail.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(viewModel: DetailViewModel, navController: NavHostController, name: String) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<DetailUIState>(
        initialValue = DetailUIState.InitState,
        key1 = lifecycle,
        key2 = viewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    val item: ItemDetail by viewModel.itemDetail.collectAsState(ItemDetail())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        Modifier
                            .height(36.dp)
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.icon_description),
                            tint = Color.Black,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(top = 2.dp)
                                .clickable {
                                    navController.popBackStack()
                                },
                        )
                        Text(
                            text = "${item.location.name} | ${item.location.country}",
                            modifier = Modifier.padding(start = 8.dp),
                            color = Color.Black,
                            fontSize = 16.sp,
                        )
                    }
                },
            )
        },
    ) {
        when (uiState) {
            is DetailUIState.Error -> ErrorStateViewDetail()
            DetailUIState.InitState -> InitStateViewDetail()
            DetailUIState.Loading -> LoadingStateViewDetail()
            is DetailUIState.Success -> SuccessStateViewDetail((uiState as DetailUIState.Success).data)
        }
    }

    viewModel.getProductDetail(name)
}
