package com.heaven.srikaya.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heaven.srikaya.data.ProductRepository
import com.heaven.srikaya.model.OrderProduct
import com.heaven.srikaya.ui.alert.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _productState: MutableStateFlow<ProductState<List<OrderProduct>>> = MutableStateFlow(ProductState.Loading)
    val productState: StateFlow<ProductState<List<OrderProduct>>>
        get() = _productState

    fun getAllProducts() {
        viewModelScope.launch {
            repository.getAllProducts()
                .catch {
                    _productState.value = ProductState.Error(it.message.toString())
                }
                .collect { orderProducts ->
                    _productState.value = ProductState.Success(orderProducts)
                }
        }
    }
}