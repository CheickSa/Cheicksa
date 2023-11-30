package com.example.cheicksa.repository

import com.example.cheicksa.model.restaurant.Stores
import com.example.cheicksa.remote.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getAllStores(): MutableList<Stores>?{
        val response = api.getAllStore()
        return withContext(Dispatchers.IO) {
            if (response.isSuccessful) response.body()
            else mutableListOf(
                Stores(
                id=0L,
                title = "error",
                description = "${response.message()}",
                image = "errror"
            )
            )
        }
    }
    suspend fun saveStore(stores: Stores): Stores {
        return api.saveStore(stores)
    }

    suspend fun saveStores(stores: List<Stores>): MutableList<Stores>{
        return api.saveStores(stores)
    }

    suspend fun updateImage(stores: Stores): Stores {
        return api.updateImage(stores)
    }

    suspend fun deleteStore(id: Long){
        return api.deleteStore(id)
    }
}