package com.example.boxgroceries.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.boxgroceries.ui.navigation.NavigationDestination

object HomeScreenDestination : NavigationDestination {
    override val route: String = "HomeScreen"
    override val titleRes: Int
        get() = TODO("Not yet implemented")
}


@Composable
fun HomeScreen(modifier: Modifier = Modifier, navigateTo: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxOption(navigateTo=navigateTo)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxOption(modifier: Modifier = Modifier, navigateTo: () -> Unit = {}) {
    ElevatedCard(
        onClick = navigateTo,
        modifier = modifier.size(width = 325.dp, height = 130.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Text("Clickable", Modifier.align(Alignment.Center))
        }
    }
}