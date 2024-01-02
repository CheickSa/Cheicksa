package com.example.cheicksa.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.cheicksa.model.restaurant.OrderInfo

@Dao
interface OrderDao {
    @Query("SELECT * FROM orderinfo")
    fun getAll(): LiveData<List<OrderInfo>>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insert(orderInfo: OrderInfo)

    @Delete
    suspend fun delete(orderInfo: OrderInfo)

    @Query("DELETE FROM orderinfo WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Upsert
    suspend fun upsert(orderInfo: OrderInfo)

    @Query("DELETE FROM orderinfo")
    suspend fun deleteAll()


}
