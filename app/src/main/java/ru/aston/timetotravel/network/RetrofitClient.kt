package ru.aston.timetotravel.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://vmeste.wildberries.ru/stream/api/avia-service/"
const val MEDIA_TYPE_STRING = "application/json"
const val REQUEST_BODY_STRING = "{\"startLocationCode\":\"LED\"}"

object RetrofitClient {

    private const val TIME_OUT: Long = 120

    private val gson = GsonBuilder().create()
    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

    val retrofit: RetrofitInterface by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(RetrofitInterface::class.java)
    }
}
