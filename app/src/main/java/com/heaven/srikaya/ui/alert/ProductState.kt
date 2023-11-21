package com.heaven.srikaya.ui.alert

sealed class ProductState<out T: Any?> {

    object Loading : ProductState<Nothing>()

    data class Success<out T: Any>(val data: T) : ProductState<T>()

    data class Error(val errorMessage: String) : ProductState<Nothing>()
}