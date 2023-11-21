package com.heaven.srikaya.di

import com.heaven.srikaya.data.ProductRepository


object Injection {
    fun provideRepository(): ProductRepository {
        return ProductRepository.getInstance()
    }
}