package ru.aston.timetotravel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.aston.timetotravel.model.Flight
import ru.aston.timetotravel.network.ApiState
import ru.aston.timetotravel.repository.FlightsRepository

class FlightsViewModel(private var flightsRepository: FlightsRepository) : ViewModel() {

    val apiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    lateinit var listOfFlights: List<Flight>

    init {
        getFlights()
    }
    private fun getFlights() = viewModelScope.launch {
        apiState.value = ApiState.Loading
        flightsRepository.getFlightsList()
            .catch { e ->
                apiState.value = ApiState.Failure(e)
            }.collect { data ->
                apiState.value = ApiState.Success(data)
                listOfFlights = data
            }
    }

    fun getFlightByToken(searchToken: String?): Flight = listOfFlights.first{
        it.searchToken == searchToken
    }

    fun onFavoriteIconTouched(searchToken: String) {
        listOfFlights.first { it.searchToken == searchToken }.apply {
            isLiked = !isLiked
        }
    }

    fun isLiked(token: String) = listOfFlights.first { it.searchToken == token }.isLiked
}