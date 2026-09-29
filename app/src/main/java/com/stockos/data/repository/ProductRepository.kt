package com.stockos.data.repository

import com.stockos.data.local.ProductDao
import com.stockos.data.local.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: Flow<List<ProductEntity>> = productDao.getAllProducts()

    suspend fun insertProduct(product: ProductEntity): Long {
        return productDao.insertProduct(product)
    }

    suspend fun isSkuTaken(sku: String): Boolean {
        return productDao.getProductBySku(sku.trim()) != null
    }

    suspend fun getProductCount(): Int {
        return productDao.getProductCount()
    }

    suspend fun insertAll(products: List<ProductEntity>): List<Long> {
        return productDao.insertAll(products)
    }
}
