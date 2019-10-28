package com.mtsahakis.venues.ui

interface VenueContract {

    interface View {
        fun hideInstructions()

        fun hideProgress()

        fun showProgress()

        fun populateList()

        fun showError()
    }

    interface Presenter {
        fun setUpView()

        fun onNewQuery(query: String)
    }

}