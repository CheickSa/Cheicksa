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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomDb : AppDatabase
): ViewModel() {

    private val db = Firebase.firestore
    private val docRef = db.collection("restaurantsOrders")

    fun newOrder(orderInfo: OrderInfo) {
         docRef.document(orderInfo.restaurantId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val score = document.data?.get("score").toString().toDouble()
                    val newScore = score + 1
                    docRef.document(orderInfo.restaurantId)
                        .update("score", newScore)
                } else {
                    docRef.document(orderInfo.restaurantId)
                        .set(hashMapOf("score" to 1))
                }
            }
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

    suspend fun deleteAllOrders() {
        roomDb.orderDao().deleteAllOrders()
    }




}