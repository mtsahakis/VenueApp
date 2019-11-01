package com.mtsahakis.venues

import com.google.gson.GsonBuilder
import com.mtsahakis.venues.data.*
import com.mtsahakis.venues.utils.RetrofitHelper
import com.mtsahakis.venues.utils.TrampolineSchedulerRule
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class VenueServiceTest {

    @Mock
    private lateinit var venueApi: VenueApi

    private val gson = GsonBuilder().create()

    private lateinit var venueService: VenueService

    @get:Rule
    var trampolineSchedulerRule = TrampolineSchedulerRule()

    @Before
    fun setup() {
        venueService = VenueService(
            venueApi,
            gson
        )
    }

    @Test
    fun `get venues api success`() {
        // given
        val location = "foo"
        val venues = listOf(Venue("1", "venue name", Location(listOf("city", "address"))))
        val response = Response(venues)
        val meta = Meta("200")
        val apiResponse = ApiResponse(response, meta)
        Mockito.`when`(
            venueApi.getVenues(
                location,
                VenueService.CLIENT_ID,
                VenueService.CLIENT_SECRET,
                VenueService.VERSION
            )
        ).thenReturn(
            Single.just(
                apiResponse
            )
        )

        // when
        val responseToCheck = venueService.getVenues(location).blockingGet()

        // then
        Assert.assertEquals(responseToCheck, venues)
    }

    @Test(expected = Exception::class)
    fun `get venues generic exception`() {
        // given
        val location = "foo"
        Mockito.`when`(
            venueApi.getVenues(
                location,
                VenueService.CLIENT_ID,
                VenueService.CLIENT_SECRET,
                VenueService.VERSION
            )
        ).thenReturn(
            Single.error(
                Exception()
            )
        )

        // when
        venueService.getVenues(location).blockingGet()
    }

    @Test(expected = ApiException::class)
    fun `get venues api exception`() {
        // given
        val location = "foo"
        Mockito.`when`(
            venueApi.getVenues(
                location,
                VenueService.CLIENT_ID,
                VenueService.CLIENT_SECRET,
                VenueService.VERSION
            )
        ).thenReturn(
            Single.error(
                RetrofitHelper.httpExceptionInvalidLocation()
            )
        )

        // when
        venueService.getVenues(location).blockingGet()
    }
}
