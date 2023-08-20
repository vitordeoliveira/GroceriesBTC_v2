package com.example.boxgroceries.ui.items

import androidx.compose.ui.graphics.FilterQuality
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boxgroceries.data.Item
import com.example.boxgroceries.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemsUiState(
    val listItems: List<Item> = emptyList(),
)


@HiltViewModel
class ItemsViewModel @Inject constructor(private val repository: ItemsRepository) : ViewModel() {
    val itemsUiState = repository.getAllItemsStream().
    map { ItemsUiState(it) }.
    stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(),
    initialValue = ItemsUiState()
    )


    fun addToCart(item: Item) {
        viewModelScope.launch {
            try {
                repository.updateItem(item)
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            }
        }
    }
}