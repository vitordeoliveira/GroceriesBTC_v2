package com.example.boxgroceries.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

enum class PriceType(val value: String) {
    UNIT("Uni"),
    KG("Kg")
}

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String?,
    val price_in: PriceType,
    val price_in_eur: Double,
    val price_in_btc: Double,
    val price_in_satoshi: Int,
    val quantityInCart: Double = 0.0
)

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * from items WHERE quantityInCart > 0 ORDER BY name ASC")
    fun getAllItemsInCart(): Flow<List<Item>>
}

interface ItemsRepository {
    fun getAllItemsStream(): Flow<List<Item>>
    fun getAllItemsInCartStream(): Flow<List<Item>>
    fun getItemStream(id: Int): Flow<Item?>
    suspend fun insertItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun updateItem(item: Item)
}


class OfflineItemsRepository @Inject constructor(private val itemDao: ItemDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()
    override fun getAllItemsInCartStream(): Flow<List<Item>> = itemDao.getAllItemsInCart()
    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)
    override suspend fun insertItem(item: Item) = itemDao.insert(item)
    override suspend fun deleteItem(item: Item) = itemDao.delete(item)
    override suspend fun updateItem(item: Item) = itemDao.update(item)
}