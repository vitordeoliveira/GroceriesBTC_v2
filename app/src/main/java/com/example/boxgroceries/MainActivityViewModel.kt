package com.example.boxgroceries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boxgroceries.data.Item
import com.example.boxgroceries.data.ItemsRepository
import com.example.boxgroceries.data.PriceType
import com.example.boxgroceries.ui.items.ItemsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val cartItems: List<Item> = emptyList(),
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: ItemsRepository) :
    ViewModel() {

    val homeUiState = repository
        .getAllItemsInCartStream()
        .map { HomeUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = HomeUiState()
        )

//    init {
//        viewModelScope.launch {
//            repository.insertItem(
//                Item(
//                    name = "item",
//                    description = "",
//                    price_in = PriceType.KG,
//                    price_in_btc = 10.0,
//                    price_in_eur = 10.0,
//                    price_in_satoshi = 10
//                )
//            )
//        }
//    }
}