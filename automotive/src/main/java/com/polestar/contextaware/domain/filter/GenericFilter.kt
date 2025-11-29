package com.polestar.contextaware.domain.filter

import com.polestar.contextaware.data.model.FilteredLocation
import com.polestar.contextaware.data.model.RawLocation

/**
 * Default fallback if car model is unknown.
 */
class GenericFilter : LocationFilterAlgorithm {
    override fun filter(raw: RawLocation): FilteredLocation {
        return FilteredLocation(raw.latitude, raw.longitude, 0.5f)
    }
}