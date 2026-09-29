package com.stockos.`data`.local

import android.database.Cursor
import android.os.CancellationSignal
import androidx.room.CoroutinesRoom
import androidx.room.CoroutinesRoom.Companion.execute
import androidx.room.EntityInsertionAdapter
import androidx.room.RoomDatabase
import androidx.room.RoomSQLiteQuery
import androidx.room.RoomSQLiteQuery.Companion.acquire
import androidx.room.util.createCancellationSignal
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.query
import androidx.sqlite.db.SupportSQLiteStatement
import java.lang.Class
import java.util.ArrayList
import java.util.concurrent.Callable
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.jvm.JvmStatic
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION"])
public class ProductDao_Impl(
  __db: RoomDatabase,
) : ProductDao {
  private val __db: RoomDatabase

  private val __insertionAdapterOfProductEntity: EntityInsertionAdapter<ProductEntity>
  init {
    this.__db = __db
    this.__insertionAdapterOfProductEntity = object : EntityInsertionAdapter<ProductEntity>(__db) {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `products` (`id`,`name`,`sku`,`category`,`location`,`currentStock`,`minimumStock`,`unitPrice`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SupportSQLiteStatement, entity: ProductEntity) {
        statement.bindLong(1, entity.id)
        statement.bindString(2, entity.name)
        statement.bindString(3, entity.sku)
        statement.bindString(4, entity.category)
        statement.bindString(5, entity.location)
        statement.bindLong(6, entity.currentStock.toLong())
        statement.bindLong(7, entity.minimumStock.toLong())
        statement.bindDouble(8, entity.unitPrice)
      }
    }
  }

  public override suspend fun insertProduct(product: ProductEntity): Long =
      CoroutinesRoom.execute(__db, true, object : Callable<Long> {
    public override fun call(): Long {
      __db.beginTransaction()
      try {
        val _result: Long = __insertionAdapterOfProductEntity.insertAndReturnId(product)
        __db.setTransactionSuccessful()
        return _result
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override suspend fun insertAll(products: List<ProductEntity>): List<Long> =
      CoroutinesRoom.execute(__db, true, object : Callable<List<Long>> {
    public override fun call(): List<Long> {
      __db.beginTransaction()
      try {
        val _result: List<Long> = __insertionAdapterOfProductEntity.insertAndReturnIdsList(products)
        __db.setTransactionSuccessful()
        return _result
      } finally {
        __db.endTransaction()
      }
    }
  })

  public override fun getAllProducts(): Flow<List<ProductEntity>> {
    val _sql: String = "SELECT * FROM products ORDER BY id ASC"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("products"), object :
        Callable<List<ProductEntity>> {
      public override fun call(): List<ProductEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfName: Int = getColumnIndexOrThrow(_cursor, "name")
          val _cursorIndexOfSku: Int = getColumnIndexOrThrow(_cursor, "sku")
          val _cursorIndexOfCategory: Int = getColumnIndexOrThrow(_cursor, "category")
          val _cursorIndexOfLocation: Int = getColumnIndexOrThrow(_cursor, "location")
          val _cursorIndexOfCurrentStock: Int = getColumnIndexOrThrow(_cursor, "currentStock")
          val _cursorIndexOfMinimumStock: Int = getColumnIndexOrThrow(_cursor, "minimumStock")
          val _cursorIndexOfUnitPrice: Int = getColumnIndexOrThrow(_cursor, "unitPrice")
          val _result: MutableList<ProductEntity> = ArrayList<ProductEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: ProductEntity
            val _tmpId: Long
            _tmpId = _cursor.getLong(_cursorIndexOfId)
            val _tmpName: String
            _tmpName = _cursor.getString(_cursorIndexOfName)
            val _tmpSku: String
            _tmpSku = _cursor.getString(_cursorIndexOfSku)
            val _tmpCategory: String
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory)
            val _tmpLocation: String
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation)
            val _tmpCurrentStock: Int
            _tmpCurrentStock = _cursor.getInt(_cursorIndexOfCurrentStock)
            val _tmpMinimumStock: Int
            _tmpMinimumStock = _cursor.getInt(_cursorIndexOfMinimumStock)
            val _tmpUnitPrice: Double
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice)
            _item =
                ProductEntity(_tmpId,_tmpName,_tmpSku,_tmpCategory,_tmpLocation,_tmpCurrentStock,_tmpMinimumStock,_tmpUnitPrice)
            _result.add(_item)
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override fun searchByName(query: String): Flow<List<ProductEntity>> {
    val _sql: String = "SELECT * FROM products WHERE name LIKE '%' || ? || '%'"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, query)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("products"), object :
        Callable<List<ProductEntity>> {
      public override fun call(): List<ProductEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfName: Int = getColumnIndexOrThrow(_cursor, "name")
          val _cursorIndexOfSku: Int = getColumnIndexOrThrow(_cursor, "sku")
          val _cursorIndexOfCategory: Int = getColumnIndexOrThrow(_cursor, "category")
          val _cursorIndexOfLocation: Int = getColumnIndexOrThrow(_cursor, "location")
          val _cursorIndexOfCurrentStock: Int = getColumnIndexOrThrow(_cursor, "currentStock")
          val _cursorIndexOfMinimumStock: Int = getColumnIndexOrThrow(_cursor, "minimumStock")
          val _cursorIndexOfUnitPrice: Int = getColumnIndexOrThrow(_cursor, "unitPrice")
          val _result: MutableList<ProductEntity> = ArrayList<ProductEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: ProductEntity
            val _tmpId: Long
            _tmpId = _cursor.getLong(_cursorIndexOfId)
            val _tmpName: String
            _tmpName = _cursor.getString(_cursorIndexOfName)
            val _tmpSku: String
            _tmpSku = _cursor.getString(_cursorIndexOfSku)
            val _tmpCategory: String
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory)
            val _tmpLocation: String
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation)
            val _tmpCurrentStock: Int
            _tmpCurrentStock = _cursor.getInt(_cursorIndexOfCurrentStock)
            val _tmpMinimumStock: Int
            _tmpMinimumStock = _cursor.getInt(_cursorIndexOfMinimumStock)
            val _tmpUnitPrice: Double
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice)
            _item =
                ProductEntity(_tmpId,_tmpName,_tmpSku,_tmpCategory,_tmpLocation,_tmpCurrentStock,_tmpMinimumStock,_tmpUnitPrice)
            _result.add(_item)
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override fun searchBySku(query: String): Flow<List<ProductEntity>> {
    val _sql: String = "SELECT * FROM products WHERE sku LIKE '%' || ? || '%'"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, query)
    return CoroutinesRoom.createFlow(__db, false, arrayOf("products"), object :
        Callable<List<ProductEntity>> {
      public override fun call(): List<ProductEntity> {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfName: Int = getColumnIndexOrThrow(_cursor, "name")
          val _cursorIndexOfSku: Int = getColumnIndexOrThrow(_cursor, "sku")
          val _cursorIndexOfCategory: Int = getColumnIndexOrThrow(_cursor, "category")
          val _cursorIndexOfLocation: Int = getColumnIndexOrThrow(_cursor, "location")
          val _cursorIndexOfCurrentStock: Int = getColumnIndexOrThrow(_cursor, "currentStock")
          val _cursorIndexOfMinimumStock: Int = getColumnIndexOrThrow(_cursor, "minimumStock")
          val _cursorIndexOfUnitPrice: Int = getColumnIndexOrThrow(_cursor, "unitPrice")
          val _result: MutableList<ProductEntity> = ArrayList<ProductEntity>(_cursor.getCount())
          while (_cursor.moveToNext()) {
            val _item: ProductEntity
            val _tmpId: Long
            _tmpId = _cursor.getLong(_cursorIndexOfId)
            val _tmpName: String
            _tmpName = _cursor.getString(_cursorIndexOfName)
            val _tmpSku: String
            _tmpSku = _cursor.getString(_cursorIndexOfSku)
            val _tmpCategory: String
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory)
            val _tmpLocation: String
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation)
            val _tmpCurrentStock: Int
            _tmpCurrentStock = _cursor.getInt(_cursorIndexOfCurrentStock)
            val _tmpMinimumStock: Int
            _tmpMinimumStock = _cursor.getInt(_cursorIndexOfMinimumStock)
            val _tmpUnitPrice: Double
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice)
            _item =
                ProductEntity(_tmpId,_tmpName,_tmpSku,_tmpCategory,_tmpLocation,_tmpCurrentStock,_tmpMinimumStock,_tmpUnitPrice)
            _result.add(_item)
          }
          return _result
        } finally {
          _cursor.close()
        }
      }

      protected fun finalize() {
        _statement.release()
      }
    })
  }

  public override suspend fun getProductBySku(sku: String): ProductEntity? {
    val _sql: String = "SELECT * FROM products WHERE LOWER(sku) = LOWER(?) LIMIT 1"
    val _statement: RoomSQLiteQuery = acquire(_sql, 1)
    var _argIndex: Int = 1
    _statement.bindString(_argIndex, sku)
    val _cancellationSignal: CancellationSignal? = createCancellationSignal()
    return execute(__db, false, _cancellationSignal, object : Callable<ProductEntity?> {
      public override fun call(): ProductEntity? {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _cursorIndexOfId: Int = getColumnIndexOrThrow(_cursor, "id")
          val _cursorIndexOfName: Int = getColumnIndexOrThrow(_cursor, "name")
          val _cursorIndexOfSku: Int = getColumnIndexOrThrow(_cursor, "sku")
          val _cursorIndexOfCategory: Int = getColumnIndexOrThrow(_cursor, "category")
          val _cursorIndexOfLocation: Int = getColumnIndexOrThrow(_cursor, "location")
          val _cursorIndexOfCurrentStock: Int = getColumnIndexOrThrow(_cursor, "currentStock")
          val _cursorIndexOfMinimumStock: Int = getColumnIndexOrThrow(_cursor, "minimumStock")
          val _cursorIndexOfUnitPrice: Int = getColumnIndexOrThrow(_cursor, "unitPrice")
          val _result: ProductEntity?
          if (_cursor.moveToFirst()) {
            val _tmpId: Long
            _tmpId = _cursor.getLong(_cursorIndexOfId)
            val _tmpName: String
            _tmpName = _cursor.getString(_cursorIndexOfName)
            val _tmpSku: String
            _tmpSku = _cursor.getString(_cursorIndexOfSku)
            val _tmpCategory: String
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory)
            val _tmpLocation: String
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation)
            val _tmpCurrentStock: Int
            _tmpCurrentStock = _cursor.getInt(_cursorIndexOfCurrentStock)
            val _tmpMinimumStock: Int
            _tmpMinimumStock = _cursor.getInt(_cursorIndexOfMinimumStock)
            val _tmpUnitPrice: Double
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice)
            _result =
                ProductEntity(_tmpId,_tmpName,_tmpSku,_tmpCategory,_tmpLocation,_tmpCurrentStock,_tmpMinimumStock,_tmpUnitPrice)
          } else {
            _result = null
          }
          return _result
        } finally {
          _cursor.close()
          _statement.release()
        }
      }
    })
  }

  public override suspend fun getProductCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM products"
    val _statement: RoomSQLiteQuery = acquire(_sql, 0)
    val _cancellationSignal: CancellationSignal? = createCancellationSignal()
    return execute(__db, false, _cancellationSignal, object : Callable<Int> {
      public override fun call(): Int {
        val _cursor: Cursor = query(__db, _statement, false, null)
        try {
          val _result: Int
          if (_cursor.moveToFirst()) {
            val _tmp: Int
            _tmp = _cursor.getInt(0)
            _result = _tmp
          } else {
            _result = 0
          }
          return _result
        } finally {
          _cursor.close()
          _statement.release()
        }
      }
    })
  }

  public companion object {
    @JvmStatic
    public fun getRequiredConverters(): List<Class<*>> = emptyList()
  }
}
