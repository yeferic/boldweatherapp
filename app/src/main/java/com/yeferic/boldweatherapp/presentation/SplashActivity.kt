package com.yeferic.boldweatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.yeferic.boldweatherapp.R
import com.yeferic.boldweatherapp.core.ui.theme.BoldWeatherAppTheme
import com.yeferic.boldweatherapp.core.ui.widgets.LottieAnimationWidget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.Default) {
            delay(3.seconds)
            goToSearchActivity()
        }

        setContent {
            BoldWeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        LottieAnimationWidget(
                            modifier = Modifier.size(128.dp),
                            id = R.raw.splash_icon,
                        )
                    }
                }
            }
        }
    }

    private fun goToSearchActivity() {
        startActivity(MainActivity.getIntent(this@SplashActivity))
        finish()
    }
}
