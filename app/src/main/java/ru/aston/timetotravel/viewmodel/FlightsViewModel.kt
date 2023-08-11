package ru.aston.timetotravel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.aston.timetotravel.model.Flight
import ru.aston.timetotravel.network.ApiState
import ru.aston.timetotravel.repository.FlightsRepository

class FlightsViewModel(private var flightsRepository: FlightsRepository) : ViewModel() {

    private val _apiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val apiState: StateFlow<ApiState>
        get() = _apiState

    private val _flightsList = MutableLiveData<List<Flight>>()
    val flightsList: LiveData<List<Flight>>
        get() = _flightsList

    init {
        getFlights()
    }
    private fun getFlights() = viewModelScope.launch {
        _apiState.value = ApiState.Loading
        flightsRepository.getFlightsList()
            .catch { e ->
                _apiState.value = ApiState.Failure(e)
            }.collect { data ->
                _apiState.value = ApiState.Success(data)
                _flightsList.value = data
            }
    }

    fun getFlightByToken(searchToken: String?): Flight? = _flightsList.value?.first{
        it.searchToken == searchToken
    }

    fun onFavoriteIconTouched(searchToken: String) {
        _flightsList.value!!.first { it.searchToken == searchToken }.apply {
            isLiked = !isLiked
        }
    }

    fun isLiked(token: String) = _flightsList.value!!.first { it.searchToken == token }.isLiked
}