package com.heaven.srikaya.model

data class Product(
    val id: Long,
    val image: Int,
    val title: String,
    val productDesc: String,
    val requiredPrice: Int,
)