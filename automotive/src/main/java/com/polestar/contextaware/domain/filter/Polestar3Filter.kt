package com.polestar.contextaware.domain.filter

import com.polestar.contextaware.data.model.FilteredLocation
import com.polestar.contextaware.data.model.RawLocation

/**
 * MOCK IMPLEMENTATION B
 * Algorithm specific to Polestar 3 (perhaps different sensors).
 */
class Polestar3Filter : LocationFilterAlgorithm {
    override fun filter(raw: RawLocation): FilteredLocation {
        // Simulation of a different logic path
        return FilteredLocation(
            latitude = raw.latitude,
            longitude = raw.longitude - 0.0002,
            confidenceLevel = 0.99f
        )
    }
}