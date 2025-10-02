package com.garan.skipjack

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.garan.skipjack.definitions.TuningConfig
import com.garan.skipjack.presentation.TuningScreen
import com.garan.skipjack.theme.SkipjackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TuningActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configId = intent?.extras?.getString(EXTRA_TUNING_CONFIG_NAME)
        val config = if (configId != null) {
            TuningConfig.valueOf(configId)
        } else {
            TuningConfig.WHOLE_NOTES
        }
        setContent {
            SkipjackTheme {
                TuningScreen(config)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setScreenOn()
    }

    override fun onPause() {
        super.onPause()
        setScreenOff()
    }

    private fun setScreenOn() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun setScreenOff() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}