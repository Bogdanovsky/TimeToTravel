package ru.aston.timetotravel.network

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.aston.timetotravel.model.ResponseWrapper

interface RetrofitInterface {

    @Headers(
        "authority: vmeste.wildberries.ru",
        "accept: application/json, text/plain, */*",
        "cache-control: no-cache",
        "content-type: application/json",
        "origin: https://vmeste.wildberries.ru",
        "pragma: no-cache",
        "referer: https://vmeste.wildberries.ru/avia",
        "sec-fetch-dest: empty",
        "sec-fetch-mode: cors",
        "sec-fetch-site: same-origin"
    )
    @POST("v1/suggests/getCheap")
    suspend fun getFlightsList(@Body requestBody: RequestBody): ResponseWrapper

}













