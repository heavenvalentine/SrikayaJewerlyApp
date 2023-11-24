package com.heaven.srikaya

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.heaven.srikaya.model.ProductDataSource
import com.heaven.srikaya.ui.SriKayaApp
import com.heaven.srikaya.ui.navigation.Screen
import com.heaven.srikaya.ui.theme.SriKayaTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SrikayaAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SriKayaTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                SriKayaApp(navController = navController)
            }
        }
    }

    @Test
    fun checkNavHostStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun checkNavigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("ProductList").performScrollToIndex(4)
        composeTestRule.onNodeWithText(ProductDataSource.dummyProducts[4].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailProduct.route)

        composeTestRule.onNodeWithText(ProductDataSource.dummyProducts[4].title).assertIsDisplayed()
    }

    @Test
    fun checkBottomNavigationWorking() {
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun checkClickItemNavigatesBack() {
        composeTestRule.onNodeWithTag("ProductList").performScrollToIndex(4)
        composeTestRule.onNodeWithText(ProductDataSource.dummyProducts[4].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailProduct.route)

        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun checkCheckoutRightBackStack() {
        composeTestRule.onNodeWithText(ProductDataSource.dummyProducts[5].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailProduct.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)

        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}