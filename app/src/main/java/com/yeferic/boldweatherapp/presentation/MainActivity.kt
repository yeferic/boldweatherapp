package com.yeferic.boldweatherapp.presentation

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.yeferic.boldweatherapp.core.ui.theme.BoldWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoldWeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
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
