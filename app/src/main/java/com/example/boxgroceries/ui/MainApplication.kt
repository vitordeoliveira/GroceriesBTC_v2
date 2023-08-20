package com.example.boxgroceries.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boxgroceries.MainActivityViewModel
import com.example.boxgroceries.R
import com.example.boxgroceries.data.Item
import com.example.boxgroceries.ui.items.ItemCard
import com.example.boxgroceries.ui.items.ItemCardPriceTag
import com.example.boxgroceries.ui.items.ItemCardTitle
import com.example.boxgroceries.ui.navigation.AppNavGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationApp(
    viewModel: MainActivityViewModel = hiltViewModel(),
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val uiState by viewModel.homeUiState.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(Modifier.width(360.dp)) {
                Box(Modifier.offset(x = 310.dp, y = 350.dp)) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ChevronLeft, contentDescription = "",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                CartList(uiState.cartItems)

            }
        },
        content = {
            Scaffold(
                topBar = {
                    BTCBoxAppBar(onClickCart = {
                        scope.launch {
                            drawerState.open()
                        }
                    })
                }
            ) {
                AppNavGraph(modifier = Modifier.padding(it))
            }
        }
    )
}

@Composable
fun CartList(cartItems: List<Item> = emptyList()){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(cartItems) {
            ItemCard(
                item = it,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BTCBoxAppBar(
    modifier: Modifier = Modifier,
    onClickCart: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                stringResource(id = R.string.app_name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            IconButton(onClick = { onClickCart() }) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }, modifier = Modifier.size(35.dp)) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = { /* doSomething() */ }, modifier = Modifier.size(35.dp)) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* doSomething() */ }, modifier = Modifier.size(35.dp)) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Localized description"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}