package com.heaven.srikaya.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heaven.srikaya.di.Injection
import com.heaven.srikaya.model.OrderProduct
import com.heaven.srikaya.ui.ViewModelFactory
import com.heaven.srikaya.ui.alert.ProductState
import com.heaven.srikaya.ui.components.ProductItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.productState.collectAsState(initial = ProductState.Loading).value.let { uiState ->
        when (uiState) {
            is ProductState.Loading -> {
                viewModel.getAllProducts()
            }
            is ProductState.Success -> {
                HomeContent(
                    orderProduct = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is ProductState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderProduct: List<OrderProduct>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(orderProduct) { data ->
            ProductItem(
                image = data.product.image,
                title = data.product.title,
                requiredPoint = data.product.requiredPrice,
                modifier = Modifier.clickable {
                    navigateToDetail(data.product.id)
                }
            )
        }
    }
}