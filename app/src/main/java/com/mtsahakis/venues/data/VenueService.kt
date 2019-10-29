package com.mtsahakis.venues.data

import com.mtsahakis.venues.BuildConfig

import io.reactivex.Single

class VenueService(private val venueApi: VenueApi) {

    fun getVenues(near: String): Single<List<Venue>> {
        return venueApi.getVenues(near, CLIENT_ID, CLIENT_SECRET, VERSION)
            .map { it.response.venues }
    }

    companion object {
        private const val CLIENT_ID = BuildConfig.CLIENT_ID
        private const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
        private const val VERSION = "20191001"
    }
}
