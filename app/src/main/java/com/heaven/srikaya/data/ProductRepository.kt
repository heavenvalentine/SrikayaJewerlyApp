package com.heaven.srikaya.data

import com.heaven.srikaya.model.OrderProduct
import com.heaven.srikaya.model.ProductDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ProductRepository {

    private val orderProducts = mutableListOf<OrderProduct>()

    init {
        if (orderProducts.isEmpty()) {
            ProductDataSource.dummyProducts.forEach {
                orderProducts.add(OrderProduct(it, 0))
            }
        }
    }

    fun getAllProducts(): Flow<List<OrderProduct>> {
        return flowOf(orderProducts)
    }

    fun getOrderProductById(productId: Long): OrderProduct {
        return orderProducts.first {
            it.product.id == productId
        }
    }

    fun updateOrderProduct(productId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderProducts.indexOfFirst { it.product.id == productId }
        val result = if (index >= 0) {
            val orderProduct = orderProducts[index]
            orderProducts[index] =
                orderProduct.copy(product = orderProduct.product, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderProducts(): Flow<List<OrderProduct>> {
        return getAllProducts()
            .map { orderProducts ->
                orderProducts.filter { orderProduct ->
                    orderProduct.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null

        fun getInstance(): ProductRepository =
            instance ?: synchronized(this) {
                ProductRepository().apply {
                    instance = this
                }
            }
    }
}