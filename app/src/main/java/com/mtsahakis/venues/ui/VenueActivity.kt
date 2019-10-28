package com.mtsahakis.venues.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.mtsahakis.venues.R
import kotlinx.android.synthetic.main.activity_venue.*


class VenueActivity : AppCompatActivity(), VenueContract.View {

    private lateinit var presenter: VenueContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        presenter = VenuePresenter(this)

        if (savedInstanceState == null) {
            presenter.setUpView()
        }
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
                    //no-op
                    return false
                }
            })
        }
        return true
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

    override fun populateList() {

    }

    override fun showError() {

    }
}