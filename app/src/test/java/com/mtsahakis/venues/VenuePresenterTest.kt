package com.mtsahakis.venues

import com.mtsahakis.venues.data.Location
import com.mtsahakis.venues.data.Venue
import com.mtsahakis.venues.data.VenueService
import com.mtsahakis.venues.ui.VenueContract
import com.mtsahakis.venues.ui.VenuePresenter
import com.mtsahakis.venues.utils.RetrofitHelper.apiExceptionInvalidLocation
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
    fun `on new query success`() {
        // given
        val location = "foo"
        val venues = listOf(Venue("1", "venue name", Location(listOf("city", "address"))))
        `when`(venueService.getVenues(location)).thenReturn(
            Single.just(
                venues
            )
        )

        // when
        mPresenter.onNewQuery(location)

        // then
        verify(view).showProgress()
        verify(view).hideProgress()
        verify(view).hideInstructions()
        verify(view).notifyRecycler(venues)
    }

    @Test
    fun `on new query api exception no error detail`() {
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
        verify(view).showProgress()
        verify(view).hideProgress()
        verify(view).showError()
    }

    @Test
    fun `on new query api exception with error detail`() {
        // given
        val location = "foo"
        val apiException = apiExceptionInvalidLocation()
        `when`(venueService.getVenues(location)).thenReturn(
            Single.error(
                apiException
            )
        )

        // when
        mPresenter.onNewQuery(location)

        // then
        verify(view).showProgress()
        verify(view).hideProgress()
        verify(view).showError(apiException.errorDetail!!)
    }

    @Test
    fun `on new query io exception`() {
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
        verify(view).showProgress()
        verify(view).hideProgress()
        verify(view).showNetworkError()
    }

    @Test
    fun `on new query generic exception`() {
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
        verify(view).showProgress()
        verify(view).hideProgress()
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