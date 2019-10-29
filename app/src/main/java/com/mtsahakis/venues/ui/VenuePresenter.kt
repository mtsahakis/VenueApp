package com.mtsahakis.venues.ui

import com.mtsahakis.venues.data.Venue
import com.mtsahakis.venues.data.VenueService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class VenuePresenter(
    private val view: VenueContract.View,
    private val venueService: VenueService,
    private val compositeDisposable: CompositeDisposable
) : VenueContract.Presenter {

    override fun setUpView() {
        view.hideProgress()
    }

    override fun onNewQuery(query: String) {
        compositeDisposable.add(
            venueService.getVenues(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Venue>>() {

                    override fun onSuccess(venues: List<Venue>) {
                        onSuccessfulResult(venues)
                    }

                    override fun onError(e: Throwable) {
                        onErrorResult(e)
                    }
                })
        )
    }

    override fun unsubscribe() {
        compositeDisposable.dispose()
    }

    private fun onSuccessfulResult(venues: List<Venue>) {
        Timber.d("response: $venues")
    }

    private fun onErrorResult(e: Throwable) {
        Timber.e(e)
    }
}