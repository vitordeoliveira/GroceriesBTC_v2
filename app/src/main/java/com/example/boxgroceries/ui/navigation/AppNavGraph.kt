package com.example.boxgroceries.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.boxgroceries.ui.home.HomeScreen
import com.example.boxgroceries.ui.home.HomeScreenDestination
import com.example.boxgroceries.ui.items.ItemsDestination
import com.example.boxgroceries.ui.items.ItemsScreen
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
//    itemsViewModel: ItemsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    NavHost(modifier = modifier, navController = navController, startDestination = HomeScreenDestination.route){
        composable(route = HomeScreenDestination.route){
            HomeScreen(navigateTo = { navController.navigate(ItemsDestination.route) })
        }
        composable(route = ItemsDestination.route){
            ItemsScreen()
        }
    }
}