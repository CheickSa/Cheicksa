package com.example.cheicksa.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.cheicksa.AppDatabase
import com.example.cheicksa.model.gpt.Chat
import com.example.cheicksa.model.restaurant.OrderInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomDb : AppDatabase
): ViewModel() {

    fun cloudMessage() {
        //FirebaseInstanceIdService()
    }

    init {
        viewModelScope.launch {
            roomDb.orderDao().deleteAll()
        }
    }

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

    suspend fun deleteOrderById(id: Long) {
        roomDb.orderDao().deleteById(id)
    }




}