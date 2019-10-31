package com.mtsahakis.venues

import com.mtsahakis.venues.data.VenueService
import com.mtsahakis.venues.ui.VenueContract
import com.mtsahakis.venues.ui.VenuePresenter
import com.mtsahakis.venues.utils.TrampolineSchedulerRule
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class VenuePresenterTest {

    @Mock
    private lateinit var view: VenueContract.View

    @Mock
    private lateinit var venueService: VenueService

    @Mock
    private lateinit var compositeDisposable: CompositeDisposable

    @get:Rule
    val mTrampolineSchedulerRule = TrampolineSchedulerRule()

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
    fun test() {

    }

}