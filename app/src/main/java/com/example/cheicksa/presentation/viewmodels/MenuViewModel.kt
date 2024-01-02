package com.example.cheicksa.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cheicksa.model.restaurant.Cuisine
import com.example.cheicksa.model.restaurant.OrderScreenCardData
import com.example.cheicksa.model.restaurant.RestaurantData
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.FileInputStream


class MenuViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val docRef = db.collection("restaurants")
    private val docRef2 = db.collection("cuısines") // ı in state of i in 1st position

    val loading = mutableStateOf(false)
    val cuisineLoading = mutableStateOf(false)

    private val _restaurants = MutableStateFlow(listOf<RestaurantData>())
    val restaurants: StateFlow<List<RestaurantData>> = _restaurants

    private val _restaurant = MutableStateFlow(RestaurantData())
    val restaurant: StateFlow<RestaurantData> = _restaurant

    private val _cuisines = MutableStateFlow(listOf<Cuisine>())
    val cuisines: StateFlow<List<Cuisine>> = _cuisines

    private val _cuisine = MutableStateFlow(Cuisine())
    val cuisine: StateFlow<Cuisine> = _cuisine

    private val orderData = MutableStateFlow(OrderScreenCardData())
    val order: StateFlow<OrderScreenCardData> = orderData


    private val _cartRestaurant = MutableStateFlow(RestaurantData())
    val cartRestaurant: StateFlow<RestaurantData> = _cartRestaurant
    private val _cart = MutableStateFlow(listOf<OrderScreenCardData>())
    val cart: StateFlow<List<OrderScreenCardData>> = _cart




    init {
        getRestaurantData()
        getCuisines()
    }

    private fun getRestaurantData() {
        cuisineLoading.value = true
        docRef.get()
            .addOnSuccessListener { result ->
                cuisineLoading.value = false
                for (document in result) {
                    _restaurants.value += listOf(document.toObject(RestaurantData::class.java))
                    Log.d("db"," ${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener {
                Log.d("TAG", "Error getting documents: ", it)
            }
    }

    private fun getCuisines() {
        loading.value = true
        docRef2.get()
            .addOnSuccessListener { result ->
                val list = mutableListOf<Cuisine>()
                for (document in result) {
                    val cuisine = document.toObject(Cuisine::class.java)
                    Log.d("TAG", "${document.id} => ${document.data}")
                    list.add(cuisine)
                }
                _cuisines.value = list
                loading.value = false

            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }
    }

    fun setRestaurant(restaurantData: RestaurantData){
        _restaurant.value = restaurantData
    }
    fun setOrder(orderScreenCardData: OrderScreenCardData){
        orderData.value = orderScreenCardData
    }

    fun setCuisine(cuisine: Cuisine){
        _cuisine.value = cuisine
    }

    fun setCartRestaurant(restaurantData: RestaurantData){
        _cartRestaurant.value = restaurantData
    }
    fun setCart(orderScreenCardData: OrderScreenCardData){
        _cart.value += listOf(orderScreenCardData)
    }
}