package com.stockos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stockos.ui.screens.AddProductScreen
import com.stockos.ui.screens.InventoryScreen
import com.stockos.ui.theme.StockOSTheme
import com.stockos.viewmodel.InventoryViewModel
import com.stockos.viewmodel.InventoryViewModelFactory

class MainActivity : ComponentActivity() {

    private val inventoryViewModel: InventoryViewModel by viewModels {
        InventoryViewModelFactory((application as StockApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockOSTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "inventory",
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable("inventory") {
                        InventoryScreen(
                            viewModel = inventoryViewModel,
                            onNavigateToAddProduct = {
                                inventoryViewModel.resetForm()
                                navController.navigate("add_product")
                            }
                        )
                    }

                    composable("add_product") {
                        AddProductScreen(
                            viewModel = inventoryViewModel,
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
