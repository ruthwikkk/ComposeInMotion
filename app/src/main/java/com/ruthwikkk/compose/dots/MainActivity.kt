package com.ruthwikkk.compose.dots

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.ruthwikkk.compose.dots.ui.composable.*
import com.ruthwikkk.compose.dots.ui.theme.DotsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DotsTheme {
                Surface(color = MaterialTheme.colors.background) {

                    // Dots()
                    // Helix()
                    // XLVI()
                    // Square()
                    // Spinner()
                    // Spinner2()
                    // ShapesLoader()
                    MovingDots()
                }
            }
        }
    }
}

