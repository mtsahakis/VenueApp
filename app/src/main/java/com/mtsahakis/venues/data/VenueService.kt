package com.mtsahakis.venues.data

import com.google.gson.Gson
import com.mtsahakis.venues.BuildConfig

import io.reactivex.Single
import retrofit2.HttpException

class VenueService(private val venueApi: VenueApi, private val gson: Gson) {

    companion object {
        private const val CLIENT_ID = BuildConfig.CLIENT_ID
        private const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
        private const val VERSION = "20191001"
    }

    fun getVenues(near: String): Single<List<Venue>> {
        return venueApi.getVenues(near, CLIENT_ID, CLIENT_SECRET, VERSION)
            .map { it.response.venues }
            .onErrorResumeNext { e -> Single.error(parseApiError(e)) }
    }

    private fun parseApiError(error: Throwable): Throwable {
        return if (error is HttpException) {
            ApiException.build(error, gson)
        } else error
    }
}
