package ru.aston.timetotravel.model

import com.google.gson.annotations.SerializedName

data class Flight (
    @SerializedName("startDate"         ) var startDate         : String?          = null,
    @SerializedName("endDate"           ) var endDate           : String?          = null,
    @SerializedName("startLocationCode" ) var startLocationCode : String?          = null,
    @SerializedName("endLocationCode"   ) var endLocationCode   : String?          = null,
    @SerializedName("startCity"         ) var startCity         : String?          = null,
    @SerializedName("endCity"           ) var endCity           : String?          = null,
    @SerializedName("serviceClass"      ) var serviceClass      : String?          = null,
    @SerializedName("seats"             ) var seats             : ArrayList<Seats> = arrayListOf(),
    @SerializedName("price"             ) var price             : Int?             = null,
    @SerializedName("searchToken"       ) var searchToken       : String,
                                          var isLiked             : Boolean          = false
)

data class Seats (
    @SerializedName("passengerType" ) var passengerType : String? = null,
    @SerializedName("count"         ) var count         : Int?    = null
)

data class ResponseWrapper (
    @SerializedName("flights" ) var flights : ArrayList<Flight> = arrayListOf()
)