package com.mtsahakis.venues

import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber


class VenueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        if (savedInstanceState == null) {
            setUpView()
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
                    onSearchStart(query)
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    //get all text changes
                    return false
                }
            })
        }
        return true
    }

    private fun onSearchStart(query: String) {
        Timber.e("query: $query")
        hideHint()
    }

    private fun setUpView() {

    }

    private fun hideHint() {

    }
}