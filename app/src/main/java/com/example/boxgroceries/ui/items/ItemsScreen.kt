package com.example.boxgroceries.ui.items

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.boxgroceries.data.Item
import com.example.boxgroceries.data.PriceType
import com.example.boxgroceries.ui.navigation.NavigationDestination
import com.example.boxgroceries.utils.times
import com.example.groceriesbox.utils.convertToDoublePrecision
import com.example.groceriesbox.utils.convertToInteger

object ItemsDestination : NavigationDestination {
    override val route: String = "ItemsDestination"
    override val titleRes: Int
        get() = TODO("Not yet implemented")

}

@Composable
fun ItemsScreen(viewModel: ItemsViewModel = hiltViewModel()) {
    val uiState by viewModel.itemsUiState.collectAsState()
    ItemsBody(listItems = uiState.listItems)
}

@Composable
fun ItemsBody(listItems: List<Item> = emptyList()) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(listItems) {
            ItemCard(
                item = it,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    item: Item,
) {
    var expanded by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf("") }
    val keyboardController = LocalFocusManager.current


    ElevatedCard(
        onClick = {
            expanded = !expanded
            keyboardController.clearFocus()
        },
        modifier = modifier.fillMaxSize(),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .defaultMinSize(minWidth = 325.dp, minHeight = 130.dp)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ItemCardTitle(item = item, modifier = Modifier.align(Alignment.CenterHorizontally))
            ItemCardPriceTag(item = item, expanded = expanded, quantity = quantity)


            if (expanded) {
                ItemCardAddToCart(item = item, quantity = quantity, onQuantityChange = {quantity = it})
            }
        }
    }
}

@Composable
fun ItemCardTitle(item: Item, modifier: Modifier) {
    Text(
        modifier = modifier,
        text = "${item.name} / ${item.price_in.value}",
        style = MaterialTheme.typography.headlineMedium
    )
    Text(
        modifier = modifier,
        text = "${item.description}",
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
fun ItemCardPriceTag(item: Item, quantity: String, expanded: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Column {
            Text(
                "Price",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 5.dp)
            )
            Text(
                "Eur: ${item.price_in_eur}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                "Btc: ${item.price_in_btc.toBigDecimal()}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                "Sats: ${item.price_in_satoshi}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row {
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(
                    "Buying Price",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                if (item.quantityInCart == 0.0) {
                    Text(
                        "Eur: ${item.price_in_eur * quantity}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Btc: ${item.price_in_btc.toBigDecimal() * if (quantity.isNotEmpty()) quantity.toBigDecimal() else 0.0.toBigDecimal()}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Sats: ${item.price_in_satoshi * if (quantity.isNotEmpty()) quantity.toDouble() else 0.0}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Text(
                        "Eur: ${item.price_in_eur * item.quantityInCart}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Btc: ${item.price_in_btc.toBigDecimal() * item.quantityInCart.toBigDecimal()}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Sats: ${item.price_in_satoshi * item.quantityInCart}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCardAddToCart(
    item: Item,
    quantity: String,
    onQuantityChange: (e: String) -> Unit,
    viewModel: ItemsViewModel = hiltViewModel(),
) {
    val keyboardController = LocalFocusManager.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        if (item.quantityInCart == 0.0) {
            TextField(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .width(100.dp),
                value = quantity,
                onValueChange = {
                    onQuantityChange(
                        if (item.price_in == PriceType.KG) {
                            it.convertToDoublePrecision(quantity)
                        } else {
                            it.convertToInteger(quantity)
                        }
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController.clearFocus() })
            )
        }

        if (item.quantityInCart == 0.0) {
            Button(onClick = { viewModel.addToCart(item.copy(quantityInCart = quantity.toDouble())) }) {
                Text(text = "Add to Cart")
            }
        } else {
            Button(onClick = { viewModel.addToCart(item.copy(quantityInCart = 0.0)) }) {
                Text(text = "Added")
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun ItemScreenPreview(
//    items: List<Item> = FakeDataSource.listItems,
//) {
//    GroceriesBoxTheme(darkTheme = false) {
//        ItemsBody(items)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ItemScreenPreviewDark(
//    items: List<Item> = FakeDataSource.listItems,
//) {
//    GroceriesBoxTheme(darkTheme = true) {
//        ItemsBody(items)
//    }
//}