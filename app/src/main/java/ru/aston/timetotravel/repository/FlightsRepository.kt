package ru.aston.timetotravel.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.aston.timetotravel.model.Flight
import ru.aston.timetotravel.network.MEDIA_TYPE_STRING
import ru.aston.timetotravel.network.REQUEST_BODY_STRING
import ru.aston.timetotravel.network.RetrofitInterface


class FlightsRepository(private val retrofitInterface: RetrofitInterface) {

    fun getFlightsList(): Flow<List<Flight>> = flow {
        val requestBody = REQUEST_BODY_STRING.toRequestBody(MEDIA_TYPE_STRING.toMediaType())
        val p = retrofitInterface.getFlightsList(requestBody).flights
        emit(p)
    }.flowOn(Dispatchers.IO)
}