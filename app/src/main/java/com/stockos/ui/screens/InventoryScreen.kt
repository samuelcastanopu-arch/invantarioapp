package com.stockos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stockos.ui.components.CategoryFilter
import com.stockos.ui.components.InventoryStatsCard
import com.stockos.ui.components.ProductItem
import com.stockos.ui.components.ProductTableHeader
import com.stockos.ui.components.SearchBar
import com.stockos.ui.components.StockHeader
import com.stockos.ui.theme.StockBackground
import com.stockos.ui.theme.StockTextTertiary
import com.stockos.viewmodel.InventoryViewModel

@Composable
fun InventoryScreen(
    viewModel: InventoryViewModel,
    onNavigateToAddProduct: () -> Unit
) {
    val stats by viewModel.inventoryStats.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val products by viewModel.filteredProducts.collectAsState()

    Scaffold(
        containerColor = StockBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(StockBackground)
        ) {
            // Header with App Title, Date and "+ NUEVO" button
            StockHeader(
                onAddNewProduct = onNavigateToAddProduct
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Inventory Summary (4 indicators)
            InventoryStatsCard(stats = stats)

            Spacer(modifier = Modifier.height(14.dp))

            // Real-time Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Horizontal Category Filter
            CategoryFilter(
                categories = viewModel.categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.onCategorySelect(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Table Header (PRODUCTO / SKU | CATEGORÍA | STOCK | PRECIO)
            ProductTableHeader()

            // Product List
            if (products.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isNotEmpty()) {
                            "No se encontraron productos para \"$searchQuery\""
                        } else {
                            "No hay productos en esta categoría"
                        },
                        color = StockTextTertiary,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(
                        items = products,
                        key = { it.id }
                    ) { product ->
                        ProductItem(product = product)
                    }

                    // Bottom spacing for comfortable scroll
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}
