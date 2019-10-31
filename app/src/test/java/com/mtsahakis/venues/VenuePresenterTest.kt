package com.mtsahakis.venues

import com.mtsahakis.venues.data.Location
import com.mtsahakis.venues.data.Venue
import com.mtsahakis.venues.data.VenueService
import com.mtsahakis.venues.ui.VenueContract
import com.mtsahakis.venues.ui.VenuePresenter
import com.mtsahakis.venues.utils.RetrofitHelper.apiExceptionForInvalidLocation
import com.mtsahakis.venues.utils.RetrofitHelper.apiExceptionNoErrorDetail
import com.mtsahakis.venues.utils.TrampolineSchedulerRule
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class VenuePresenterTest {

    @Mock
    private lateinit var view: VenueContract.View

    @Mock
    private lateinit var venueService: VenueService

    @Mock
    private lateinit var compositeDisposable: CompositeDisposable

    @get:Rule
    val trampolineSchedulerRule = TrampolineSchedulerRule()

    private lateinit var mPresenter: VenuePresenter

    @Before
    fun setup() {
        mPresenter = VenuePresenter(
            view,
            venueService,
            compositeDisposable
        )
    }

    @Test
    fun onNewQuery_Success() {
        // given
        val location = "foo"
        val response = listOf(Venue("1", "venue name", Location(listOf("city", "address"))))
        `when`(venueService.getVenues(location)).thenReturn(
            Single.just(
                response
            )
        )

        // when
        mPresenter.onNewQuery(location)

        // then
        verify(view).hideInstructions()
        verify(view).notifyRecycler(response)
    }

    @Test
    fun onNewQuery_ApiException_No_ErrorDetail() {
        // given
        val location = "foo"
        val apiException = apiExceptionNoErrorDetail()
        `when`(venueService.getVenues(location)).thenReturn(
            Single.error(
                apiException
            )
        )

        // when
        mPresenter.onNewQuery(location)

        // then
        verify(view).showError()
    }

    @Test
    fun onNewQuery_ApiException_With_ErrorDetail() {
        // given
        val location = "foo"
        val apiException = apiExceptionForInvalidLocation()
        `when`(venueService.getVenues(location)).thenReturn(
            Single.error(
                apiException
            )
        )

        // when
        mPresenter.onNewQuery(location)

        // then
        verify(view).showError(apiException.errorDetail!!)
    }

    @Test
    fun onNewQuery_IO_Exception() {
        // given
        val location = "foo"
        `when`(venueService.getVenues(location)).thenReturn(
            Single.error(
                IOException()
            )
        )

        // when
        mPresenter.onNewQuery(location)

        // then
        verify(view).showNetworkError()
    }

    @Test
    fun onNewQuery_Generic_Exception() {
        // given
        val location = "foo"
        `when`(venueService.getVenues(location)).thenReturn(
            Single.error(
                Exception()
            )
        )

        // when
        mPresenter.onNewQuery(location)

        // then
        verify(view).showError()
    }

    @Test
    fun unsubscribe() {
        // when
        mPresenter.unsubscribe()

        // then
        verify(compositeDisposable).dispose()
    }

}