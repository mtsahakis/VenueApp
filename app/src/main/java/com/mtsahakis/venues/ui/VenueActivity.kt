package com.mtsahakis.venues.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mtsahakis.venues.R
import com.mtsahakis.venues.data.Venue
import com.mtsahakis.venues.data.VenueService
import com.mtsahakis.venues.injection.DaggerVenueComponent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_venue.*
import javax.inject.Inject


class VenueActivity : AppCompatActivity(), VenueContract.View {

    @Inject
    lateinit var venueService: VenueService

    private lateinit var presenter: VenueContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        val component = DaggerVenueComponent.create()
        component.inject(this)

        presenter = VenuePresenter(this, venueService, CompositeDisposable())
        venuesRecycler.layoutManager = LinearLayoutManager(this)
        venuesRecycler.adapter = VenueAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            queryHint = getString(R.string.search_hint)
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    presenter.onNewQuery(query)
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    return false
                }
            })
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

    override fun hideInstructions() {
        instructions.visibility = View.GONE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun notifyRecycler(venues: List<Venue>) {
        val adapter = venuesRecycler.adapter
        if (adapter is VenueAdapter) {
            adapter.venues = venues
            adapter.notifyDataSetChanged()
        }
    }

    override fun showNetworkError() {
        showSnackBar(getString(R.string.a_network_error_has_occurred))
    }

    override fun showError() {
        showSnackBar(getString(R.string.an_error_has_occurred))
    }

    override fun showError(message: String) {
        showSnackBar(getString(R.string.error_fetching_results_s, message))
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            window.decorView.rootView,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}