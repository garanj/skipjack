package com.garan.skipjack

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.garan.skipjack.navigation.SkipjackNavigation
import com.garan.skipjack.theme.SkipjackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkipjackTheme {
                SkipjackNavigation()
            }
        }
    }
}
