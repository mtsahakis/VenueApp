package com.mtsahakis.venues.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mtsahakis.venues.R
import com.mtsahakis.venues.data.Venue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_venue.view.*

internal class VenueAdapter : RecyclerView.Adapter<VenueAdapter.VenueViewHolder>() {

    internal inner class VenueViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(venue: Venue) {
            containerView.name.text = venue.name
            containerView.address.text = venue.location.formattedAddress.joinToString(", ")
        }
    }

    var venues: List<Venue> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        return VenueViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_venue,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(venues[position])
    }

    override fun getItemCount(): Int {
        return venues.size
    }

}
