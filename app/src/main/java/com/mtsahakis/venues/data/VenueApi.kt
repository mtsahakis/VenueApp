package com.mtsahakis.venues.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface VenueApi {

    @GET("v2/venues/search")
    fun getVenues(
        @Query("near") near: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version: String
    ): Single<ApiResponse>

}
