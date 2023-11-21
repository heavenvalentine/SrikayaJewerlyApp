package com.heaven.srikaya.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heaven.srikaya.data.ProductRepository
import com.heaven.srikaya.ui.alert.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _productState: MutableStateFlow<ProductState<CartState>> = MutableStateFlow(ProductState.Loading)
    val productState: StateFlow<ProductState<CartState>>
        get() = _productState

    fun getAddedOrderProducts() {
        viewModelScope.launch {
            _productState.value = ProductState.Loading
            repository.getAddedOrderProducts()
                .collect { orderProducts ->
                    val totalRequiredPoint =
                        orderProducts.sumOf { it.product.requiredPrice * it.count }
                    _productState.value = ProductState.Success(CartState(orderProducts, totalRequiredPoint))
                }
        }
    }

    fun updateOrderProduct(productId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderProduct(productId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderProducts()
                    }
                }
        }
    }
}