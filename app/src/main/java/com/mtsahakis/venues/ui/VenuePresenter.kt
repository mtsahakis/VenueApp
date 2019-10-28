package com.mtsahakis.venues.ui

class VenuePresenter(private val view: VenueContract.View) : VenueContract.Presenter {

    override fun setUpView() {
        view.hideProgress()
    }

    override fun onNewQuery(query: String) {

    }
}