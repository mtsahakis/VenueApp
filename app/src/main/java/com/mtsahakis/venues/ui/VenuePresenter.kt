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

    private var venues: List<Venue> = listOf()

    override fun setUpView() {
        view.hideProgress()
    }

    override fun onNewQuery(query: String) {
        // show progress
        view.showProgress()
        // fetch data
        compositeDisposable.add(
            venueService.getVenues(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    override fun onBindRowViewAtPosition(
        position: Int, rowViewHolder: VenueContract.RowViewHolder
    ) {
        rowViewHolder.setRecord(venues[position])
    }

    override fun getRecordCount() = venues.size

    override fun unsubscribe() {
        compositeDisposable.dispose()
    }

    private fun onSuccessfulResult(venues: List<Venue>) {
        Timber.d("response: $venues")
        this.venues = venues
        view.hideInstructions()
        view.notifyRecycler()
    }

    private fun onErrorResult(e: Throwable) {
        Timber.e(e)
    }
}