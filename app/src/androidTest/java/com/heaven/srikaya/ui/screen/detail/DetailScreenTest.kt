package com.heaven.srikaya.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.heaven.srikaya.R
import com.heaven.srikaya.model.OrderProduct
import com.heaven.srikaya.model.Product
import com.heaven.srikaya.onNodeWithStringId
import com.heaven.srikaya.ui.theme.SriKayaTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val fakeProductOrder = OrderProduct(
        product = Product(5, R.drawable.necklace1, "Exquisite Pearl Necklace", "Enhance your beauty with this exquisite pearl necklace. Its timeless charm and grace make it an essential accessory.", 1100),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SriKayaTheme {
                DetailContent(
                    fakeProductOrder.product.image,
                    fakeProductOrder.product.title,
                    fakeProductOrder.product.requiredPrice,
                    fakeProductOrder.count,
                    fakeProductOrder.product.productDesc,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun checkDetailContentTitleAndPriceDisplayed() {
        composeTestRule.onNodeWithText(fakeProductOrder.product.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.required_price,
                fakeProductOrder.product.requiredPrice
            )
        ).assertIsDisplayed()
    }

    @Test
    fun checkIncreaseProductButtonEnabledAfterIncrement() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun checkIncreaseProductCounterIsCorrectAfterMultipleIncrements() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol)
            .performClick()
            .performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun checkDecreaseProductCounterRemainsZero() {
        composeTestRule.onNodeWithStringId(R.string.minus_symbol)
            .performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}