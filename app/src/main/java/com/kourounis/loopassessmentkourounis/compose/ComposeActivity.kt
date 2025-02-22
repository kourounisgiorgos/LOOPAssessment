package com.kourounis.loopassessmentkourounis.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kourounis.loopassessmentkourounis.compose.navigation.NavGraph

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigation()
        }
    }
}

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}
