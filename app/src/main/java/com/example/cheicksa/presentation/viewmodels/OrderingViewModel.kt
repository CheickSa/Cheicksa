package com.example.cheicksa.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.cheicksa.model.Bill
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderingViewModel(
): ViewModel(){
    private var _bill = MutableStateFlow(Bill())
    val bill = _bill.asStateFlow()

    fun updateBill(bill: Bill){
        _bill.update { bill }
        _bill.update {
            it.copy(total = _bill.value.price + _bill.value.tax + _bill.value.deliveryFee)
        }
    }

    fun changePrice(increase: Boolean){
        _bill.update {
            it.copy(amount = if (increase) _bill.value.amount + 1 else _bill.value.amount - 1)
        }
        _bill.update {
            it.copy(price = _bill.value.initialPrice * _bill.value.amount,)
        }
        _bill.update {
            it.copy(tax = ((_bill.value.price).toDouble() * 0.1).toInt(),)
        }
        _bill.update {
            it.copy(total = _bill.value.price + _bill.value.tax + _bill.value.deliveryFee)
        }
    }
    fun applyDiscount(discount: Int ){
        _bill.update {
            it.copy(discount = _bill.value.total - discount)
        }
    }



}