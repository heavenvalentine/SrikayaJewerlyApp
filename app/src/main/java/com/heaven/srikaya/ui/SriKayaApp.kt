package com.heaven.srikaya.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.heaven.srikaya.R
import com.heaven.srikaya.ui.navigation.NavigationItem
import com.heaven.srikaya.ui.navigation.Screen
import com.heaven.srikaya.ui.screen.detail.DetailScreen
import com.heaven.srikaya.ui.screen.home.HomeScreen
import com.heaven.srikaya.ui.screen.profile.ProfileScreen
import com.heaven.srikaya.ui.theme.SriKayaTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SriKayaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { productId ->
                        navController.navigate(Screen.DetailProduct.createRoute(productId))
                    }
                )
            }
            composable(Screen.Cart.route) {
                val context = LocalContext.current
//                CartScreen(
//                    onOrderButtonClicked = { message ->
//                        shareOrder(context, message)
//                    }
//                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailProduct.route,
                arguments = listOf(navArgument("productId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("productId") ?: -1L
                DetailScreen(
                    productId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
//
//    Column {
//        Banner()
//    }


}

//private fun shareOrder(context: Context, summary: String) {
//    val intent = Intent(Intent.ACTION_SEND).apply {
//        type = "text/plain"
//        putExtra(Intent.EXTRA_SUBJECT, context.getString(string.dicoding_reward))
//        putExtra(Intent.EXTRA_TEXT, summary)
//    }
//
//    context.startActivity(
//        Intent.createChooser(
//            intent,
//            context.getString(string.collapsed)
//        )
//    )
//}

@Composable
fun Banner(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.banner),
            contentDescription = "Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(160.dp)
        )
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = false,
                onClick = {
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryRowPreview() {
    SriKayaTheme {
        SriKayaApp()
    }
}