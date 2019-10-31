package com.mtsahakis.venues.ui

import com.mtsahakis.venues.data.Venue

interface VenueContract {

    interface View {

        fun hideInstructions()

        fun hideProgress()

        fun showProgress()

        fun notifyRecycler()

        fun showNetworkError()

        fun showError()

        fun showError(message: String)
    }

    interface Presenter {

        fun onNewQuery(query: String)

        fun onBindRowViewAtPosition(position: Int, rowViewHolder: RowViewHolder)

        fun getRecordCount(): Int

        fun unsubscribe()
    }

    interface RowViewHolder {

        fun setRecord(venue: Venue)

    }

}