package com.mtsahakis.venues.data

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("response") var response: Response,
    @SerializedName("meta") var meta: Meta
)

data class Response(@SerializedName("venues") var venues: List<Venue>)

data class Meta(
    @SerializedName("code") var code: String,
    @SerializedName("errorDetail") var errorDetail: String? = null
)

data class Venue(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("location") var location: Location
)

data class Location(@SerializedName("formattedAddress") var formattedAddress: List<String>)
