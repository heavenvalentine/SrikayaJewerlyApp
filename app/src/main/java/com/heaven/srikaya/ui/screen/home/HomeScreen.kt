package com.heaven.srikaya.ui.screen.home

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heaven.srikaya.R
import com.heaven.srikaya.di.Injection
import com.heaven.srikaya.model.OrderProduct
import com.heaven.srikaya.ui.ViewModelFactory
import com.heaven.srikaya.ui.alert.ProductState
import com.heaven.srikaya.ui.components.ProductItem
import com.heaven.srikaya.ui.searchbar.Search

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val searchState by viewModel.searchState


    viewModel.productState.collectAsState(initial = ProductState.Loading).value.let { uiState ->

        Column(modifier = modifier) {

            when (uiState) {
                is ProductState.Loading -> {
                    viewModel.getAllProducts()
                }

                is ProductState.Success -> {
                    HomeContent(
                        orderProduct = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                        query = searchState.query,
                        onQueryChange = viewModel::onQueryChange
                    )
                }

                is ProductState.Error -> {
                    Toast.makeText(LocalContext.current,
                        stringResource(R.string.error_message), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    orderProduct: List<OrderProduct>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .height(90.dp)
            .background(Color.Transparent)
    ){
        Search(
            query = query,
            onQueryChange = onQueryChange
        )
    }

    if (orderProduct.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.no_items_avalable),
                modifier = modifier.padding(16.dp)
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.testTag("ProductList")
        ) {
            items(orderProduct, key = {it.product.id}) { data ->
                ProductItem(
                    image = data.product.image,
                    title = data.product.title,
                    requiredPrice = data.product.requiredPrice,
                    modifier = modifier
                        .clickable {
                        navigateToDetail(data.product.id) }
                        .animateItemPlacement(tween(durationMillis = 300))
                )
            }
        }
    }
}
