package com.stockos.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val sku: String,
    val category: String,
    val location: String,
    val currentStock: Int,
    val minimumStock: Int,
    val unitPrice: Double,
) {
    val totalValue: Double
        get() = currentStock * unitPrice

    val isOutOfStock: Boolean
        get() = currentStock == 0

    val isLowStock: Boolean
        get() = (currentStock in (1..minimumStock))
}
