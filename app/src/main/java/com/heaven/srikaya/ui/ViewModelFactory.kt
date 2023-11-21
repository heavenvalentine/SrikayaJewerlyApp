package com.heaven.srikaya.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heaven.srikaya.data.ProductRepository
import com.heaven.srikaya.ui.screen.cart.CartViewModel
import com.heaven.srikaya.ui.screen.detail.DetailProductViewModel
import com.heaven.srikaya.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailProductViewModel::class.java)) {
            return DetailProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}