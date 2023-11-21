package com.heaven.srikaya.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heaven.srikaya.data.ProductRepository
import com.heaven.srikaya.model.OrderProduct
import com.heaven.srikaya.model.Product
import com.heaven.srikaya.ui.alert.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _productState: MutableStateFlow<ProductState<OrderProduct>> =
        MutableStateFlow(ProductState.Loading)
    val productState: StateFlow<ProductState<OrderProduct>>
        get() = _productState

    fun getProductById(productId: Long) {
        viewModelScope.launch {
            _productState.value = ProductState.Loading
            _productState.value = ProductState.Success(repository.getOrderProductById(productId))
        }
    }

    fun addToCart(product: Product, count: Int) {
        viewModelScope.launch {
            repository.updateOrderProduct(product.id, count)
        }
    }
}