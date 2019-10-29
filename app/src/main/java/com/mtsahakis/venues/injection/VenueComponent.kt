package com.mtsahakis.venues.injection

import com.mtsahakis.venues.ui.VenueActivity

import dagger.Component

@VenueActivityScope
@Component(modules = [VenueModule::class])
interface VenueComponent {

    fun inject(venueActivity: VenueActivity)

}

