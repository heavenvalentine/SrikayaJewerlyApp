package com.heaven.srikaya.ui.screen.cart

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heaven.srikaya.R
import com.heaven.srikaya.di.Injection
import com.heaven.srikaya.ui.ViewModelFactory
import com.heaven.srikaya.ui.alert.ProductState
import com.heaven.srikaya.ui.components.OrderButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.productState.collectAsState(initial = ProductState.Loading).value.let { uiState ->
        when (uiState) {
            is ProductState.Loading -> {
                viewModel.getAddedOrderProducts()
            }
            is ProductState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { productId, count ->
                        viewModel.updateOrderProduct(productId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is ProductState.Error -> {
                Toast.makeText(LocalContext.current,
                    stringResource(R.string.error_message), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderProduct.count(),
        state.totalRequiredPrice
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.orderProduct.isEmpty()) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.no_items_in_cart))
            }
        } else {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.menu_cart),
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.weight(weight = 1f)
            ) {
                items(state.orderProduct, key = { it.product.id }) { item ->
                    CartItem(
                        productsId = item.product.id,
                        image = item.product.image,
                        title = item.product.title,
                        totalPrice = item.product.requiredPrice * item.count,
                        count = item.count,
                        onProductCountChanged = onProductCountChanged,
                    )
                    Divider()
                }
            }

            OrderButton(
                text = stringResource(R.string.total_order, state.totalRequiredPrice),
                enabled = state.orderProduct.isNotEmpty(),
                onClick = {
                    onOrderButtonClicked(shareMessage)
                },
                modifier = modifier.padding(16.dp)
            )
        }
    }
}
