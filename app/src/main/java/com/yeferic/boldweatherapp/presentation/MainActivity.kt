package com.yeferic.boldweatherapp.presentation

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yeferic.boldweatherapp.core.commons.Routes.DETAIL_SCREEN
import com.yeferic.boldweatherapp.core.commons.Routes.DETAIL_SCREEN_NAME_PARAMETER
import com.yeferic.boldweatherapp.core.commons.Routes.SEARCH_SCREEN
import com.yeferic.boldweatherapp.core.ui.theme.BoldWeatherAppTheme
import com.yeferic.boldweatherapp.presentation.detail.ui.DetailScreen
import com.yeferic.boldweatherapp.presentation.detail.viewmodel.DetailViewModel
import com.yeferic.boldweatherapp.presentation.search.ui.SearchScreen
import com.yeferic.boldweatherapp.presentation.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoldWeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = SEARCH_SCREEN,
                    ) {
                        composable(SEARCH_SCREEN) {
                            SearchScreen(searchViewModel, navigationController)
                        }
                        composable("$DETAIL_SCREEN{$DETAIL_SCREEN_NAME_PARAMETER}") {
                            val itemName =
                                it.arguments?.getString(DETAIL_SCREEN_NAME_PARAMETER).orEmpty()
                            DetailScreen(
                                viewModel = detailViewModel,
                                navController = navigationController,
                                name = itemName,
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java).apply {
            addFlags(FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(FLAG_ACTIVITY_NEW_TASK)
        }
    }
}
