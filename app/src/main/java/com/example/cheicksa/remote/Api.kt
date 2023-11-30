package com.example.cheicksa.remote

import com.example.cheicksa.model.restaurant.Stores
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface Api {
    @GET("/shop/stores")
    suspend fun getAllStore(): Response<MutableList<Stores>>

    @POST("/shop/store/save")
    suspend fun saveStore(@Body stores: Stores): Stores

    @POST("/shop/stores/save")
    suspend fun saveStores(@Body stores: List<Stores>): MutableList<Stores>

    @PUT("/shop/store/update_image")
    suspend fun updateImage(@Body stores: Stores): Stores

    @DELETE("/shop/store/delete")
    suspend fun deleteStore( @Query("id") id: Long)

    companion object {
        const val BASE_URL = "http://10.0.2.2:9090"
    }
}