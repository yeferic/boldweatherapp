package com.yeferic.boldweatherapp.presentation.search.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.yeferic.boldweatherapp.core.commons.Constants
import com.yeferic.boldweatherapp.core.ui.widgets.AnimatedVisibilityWidget
import com.yeferic.boldweatherapp.presentation.search.states.SearchUIState
import com.yeferic.boldweatherapp.presentation.search.ui.screens.ErrorStateBody
import com.yeferic.boldweatherapp.presentation.search.ui.screens.InitStateBody
import com.yeferic.boldweatherapp.presentation.search.ui.screens.LoadingStateBody
import com.yeferic.boldweatherapp.presentation.search.ui.screens.SuccessStateBody
import com.yeferic.boldweatherapp.presentation.search.ui.screens.TopBarSearchInputViewSearch
import com.yeferic.boldweatherapp.presentation.search.viewmodel.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavHostController) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<SearchUIState>(
        initialValue = SearchUIState.InitState,
        key1 = lifecycle,
        key2 = viewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    val textValue: String by viewModel.queryText.observeAsState(Constants.EMPTY_STRING)

    Scaffold(
        topBar = {
            AnimatedVisibilityWidget {
                TopBarSearchInputViewSearch(
                    textValue,
                    clearButtonAction = {
                        viewModel.clearTextAndState()
                    },
                    onTextChange = {
                        viewModel.setQueryTextValue(it)
                    },
                )
            }
        },
    ) {
        Box(Modifier.background(Color.White)) {
            AnimatedVisibilityWidget {
                when (uiState) {
                    is SearchUIState.Error -> ErrorStateBody()
                    SearchUIState.InitState -> InitStateBody()
                    SearchUIState.Loading -> LoadingStateBody()
                    is SearchUIState.SuccessSearch -> SuccessStateBody(
                        viewModel,
                        navController,
                    )
                }
            }
        }
    }
}
