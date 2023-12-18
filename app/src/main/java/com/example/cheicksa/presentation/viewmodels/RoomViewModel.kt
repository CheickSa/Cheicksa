package com.example.cheicksa.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.cheicksa.AppDatabase
import com.example.cheicksa.model.restaurant.OrderInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomDb : AppDatabase
): ViewModel() {

    fun orders(): LiveData<List<OrderInfo>> {
        return roomDb.orderDao().getAll()
    }


    suspend fun insertOrder(orderInfo: OrderInfo) {
        roomDb.orderDao().insert(orderInfo)
    }

    suspend fun deleteOrder(orderInfo: OrderInfo) {
        roomDb.orderDao().delete(orderInfo)
    }

    suspend fun upsertOrder(orderInfo: OrderInfo) {
        roomDb.orderDao().upsert(orderInfo)
    }
}