package com.example.cheicksa.presentation.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheicksa.model.Stores
import com.example.cheicksa.repository.StoreRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val repository: StoreRepository
): ViewModel() {

    private val _allStores: MutableStateFlow<MutableList<Stores>> = MutableStateFlow(mutableListOf())
    val allStores = _allStores.asStateFlow()

    fun getAllStore(){
        viewModelScope.launch {
            _allStores.value = repository.getAllStores()!!
        }
    }

    fun saveStore(stores: Stores) {
        viewModelScope.launch {
            repository.saveStore(stores)
        }
    }

    fun saveStores(stores: List<Stores>){
        viewModelScope.launch {
            repository.saveStores(stores)
        }
    }

    suspend fun updateImage(stores: Stores) {
        viewModelScope.launch {
            repository.updateImage(stores)
        }
    }

    fun deleteStore(id: Long){
        viewModelScope.launch {
            repository.deleteStore(id)
        }
    }
}