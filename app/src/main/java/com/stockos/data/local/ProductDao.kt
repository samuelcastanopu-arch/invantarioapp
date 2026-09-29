package com.stockos.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY id ASC")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>): List<Long>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%'")
    fun searchByName(query: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE sku LIKE '%' || :query || '%'")
    fun searchBySku(query: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE LOWER(sku) = LOWER(:sku) LIMIT 1")
    suspend fun getProductBySku(sku: String): ProductEntity?

    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int
}
