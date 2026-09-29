package com.stockos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class StockDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: StockDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): StockDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StockDatabase::class.java,
                    "stockos_database"
                )
                    .addCallback(StockDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateInitialData(productDao: ProductDao) {
            val initialProducts = listOf(
                ProductEntity(
                    name = "Taladro Percutor 800W",
                    sku = "HRW-0001",
                    category = "HERRAMIENTAS",
                    location = "Bodega A-3",
                    currentStock = 14,
                    minimumStock = 5,
                    unitPrice = 1850.0
                ),
                ProductEntity(
                    name = "Cable UTP Cat5 (rollo)",
                    sku = "CAB-0010",
                    category = "CABLES",
                    location = "Bodega A-2",
                    currentStock = 3,
                    minimumStock = 8,
                    unitPrice = 428.0
                ),
                ProductEntity(
                    name = "Interruptor Bipolar 20A",
                    sku = "ELEC-0021",
                    category = "ELÉCTRICO",
                    location = "Bodega B-1",
                    currentStock = 52,
                    minimumStock = 20,
                    unitPrice = 85.0
                ),
                ProductEntity(
                    name = "Pintura Esmalte Blanco 4L",
                    sku = "PIN-BL4",
                    category = "PINTURAS",
                    location = "Bodega C-2",
                    currentStock = 7,
                    minimumStock = 10,
                    unitPrice = 328.0
                ),
                ProductEntity(
                    name = "Llave Inglesa 12\"",
                    sku = "HER-LI12",
                    category = "HERRAMIENTAS",
                    location = "Bodega A-1",
                    currentStock = 0,
                    minimumStock = 3,
                    unitPrice = 329.0
                ),
                ProductEntity(
                    name = "Tubería PVC 1/2\" (tubo)",
                    sku = "PLOM-0012",
                    category = "PLOMERÍA",
                    location = "Bodega D-1",
                    currentStock = 10,
                    minimumStock = 5,
                    unitPrice = 157.0
                )
            )
            productDao.insertAll(initialProducts)
        }

        private class StockDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.productDao())
                    }
                }
            }
        }
    }
}
