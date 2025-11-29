package com.polestar.contextaware.domain.filter

import com.polestar.contextaware.data.model.FilteredLocation
import com.polestar.contextaware.data.model.RawLocation

/**
 * MOCK IMPLEMENTATION A
 * Algorithm specific to Polestar 2 hardware quirks.
 */
class Polestar2Filter : LocationFilterAlgorithm {
    override fun filter(raw: RawLocation): FilteredLocation {
        // Simulation of complex logic
        return FilteredLocation(
            latitude = raw.latitude + 0.0001, // Correction offset
            longitude = raw.longitude,
            confidenceLevel = 0.95f
        )
    }
}