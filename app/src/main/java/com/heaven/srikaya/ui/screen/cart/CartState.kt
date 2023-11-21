package com.heaven.srikaya.ui.screen.cart

import com.heaven.srikaya.model.OrderProduct

data class CartState(
    val orderProduct: List<OrderProduct>,
    val totalRequiredPoint: Int
)