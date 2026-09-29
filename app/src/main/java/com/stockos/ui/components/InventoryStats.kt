package com.stockos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stockos.ui.theme.StockBorder
import com.stockos.ui.theme.StockSurface
import com.stockos.ui.theme.StockTextPrimary
import com.stockos.ui.theme.StockTextTertiary
import com.stockos.ui.theme.StockWarning
import com.stockos.viewmodel.InventoryStats
import java.text.NumberFormat
import java.util.Locale

@Composable
fun InventoryStatsCard(
    stats: InventoryStats,
    modifier: Modifier = Modifier
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }
    val formattedTotal = currencyFormatter.format(stats.totalInventoryValue)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(StockSurface)
            .border(1.dp, StockBorder, RoundedCornerShape(6.dp))
            .padding(vertical = 14.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            StatItem(
                label = "PRODUCTOS",
                value = stats.totalProducts.toString(),
                valueColor = StockTextPrimary,
                modifier = Modifier.weight(1f)
            )

            StatDivider()

            StatItem(
                label = "AGOTADOS",
                value = stats.outOfStockCount.toString(),
                valueColor = StockWarning,
                modifier = Modifier.weight(1f)
            )

            StatDivider()

            StatItem(
                label = "STOCK BAJO",
                value = stats.lowStockCount.toString(),
                valueColor = StockWarning,
                modifier = Modifier.weight(1.1f)
            )

            StatDivider()

            StatItem(
                label = "VALOR TOTAL",
                value = formattedTotal,
                valueColor = StockTextPrimary,
                valueSize = 17.sp,
                modifier = Modifier.weight(1.4f)
            )
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    valueColor: Color,
    valueSize: TextUnit = 24.sp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        Text(
            text = label,
            color = StockTextTertiary,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            fontFamily = FontFamily.Monospace,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = valueColor,
            fontSize = valueSize,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.SansSerif,
            maxLines = 1
        )
    }
}

@Composable
private fun StatDivider() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(StockBorder)
    )
}
