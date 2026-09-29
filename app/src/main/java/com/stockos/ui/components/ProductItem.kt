package com.stockos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stockos.data.local.ProductEntity
import com.stockos.ui.theme.StockDivider
import com.stockos.ui.theme.StockError
import com.stockos.ui.theme.StockTextPrimary
import com.stockos.ui.theme.StockTextSecondary
import com.stockos.ui.theme.StockTextTertiary
import com.stockos.ui.theme.StockWarning
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductTableHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PRODUCTO\n/ SKU",
                color = StockTextTertiary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                fontFamily = FontFamily.Monospace,
                lineHeight = 13.sp,
                modifier = Modifier.weight(2.3f)
            )

            Text(
                text = "CATEGORÍA",
                color = StockTextTertiary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.weight(1.8f)
            )

            Text(
                text = "STOCK",
                color = StockTextTertiary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.weight(1.3f)
            )

            Text(
                text = "PRECIO",
                color = StockTextTertiary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1.1f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(StockDivider)
        )
    }
}

@Composable
fun ProductItem(
    product: ProductEntity,
    modifier: Modifier = Modifier
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }
    val formattedPrice = currencyFormatter.format(product.unitPrice)

    val stockColor = when {
        product.isOutOfStock -> StockError
        product.isLowStock -> StockWarning
        else -> StockTextPrimary
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Column 1: Producto / SKU
            Column(
                modifier = Modifier.weight(2.3f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.name,
                    color = StockTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 18.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = product.sku,
                    color = StockTextTertiary,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 0.5.sp
                )
            }

            // Column 2: Categoría
            Column(
                modifier = Modifier.weight(1.8f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.category.uppercase(),
                    color = StockTextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Column 3: Stock
            Row(
                modifier = Modifier.weight(1.3f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${product.currentStock}",
                    color = stockColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.width(3.dp))
                Column {
                    Text(
                        text = "/ ${product.minimumStock}",
                        color = StockTextTertiary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 12.sp
                    )
                    Text(
                        text = "min",
                        color = StockTextTertiary,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 10.sp
                    )
                }
            }

            // Column 4: Precio
            Text(
                text = formattedPrice,
                color = StockTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.weight(1.1f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(StockDivider)
        )
    }
}
