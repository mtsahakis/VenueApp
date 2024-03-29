package com.mtsahakis.venues.ui

import com.mtsahakis.venues.data.ApiException
import com.mtsahakis.venues.data.Venue
import com.mtsahakis.venues.data.VenueService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.IOException

class VenuePresenter(
    private val view: VenueContract.View,
    private val venueService: VenueService,
    private val compositeDisposable: CompositeDisposable
) : VenueContract.Presenter {

    override fun onNewQuery(query: String) {
        compositeDisposable.add(
            venueService.getVenues(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showProgress() }
                .doFinally { view.hideProgress() }
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
        view.hideInstructions()
        view.notifyRecycler(venues)
    }

    private fun onErrorResult(e: Throwable) {
        Timber.e(e)
        if (e is ApiException) {
            val errorDetail = e.errorDetail
            if (errorDetail.isNullOrBlank()) {
                view.showError()
            } else {
                view.showError(errorDetail)
            }
        } else if (e is IOException) {
            view.showNetworkError()
        } else {
            view.showError()
        }
    }
}