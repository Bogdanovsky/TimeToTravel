package ru.aston.timetotravel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.aston.timetotravel.repository.FlightsRepository

class ViewModelFactory(private val itemRepository: FlightsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FlightsViewModel(itemRepository) as T
    }
}