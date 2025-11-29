package com.polestar.contextaware.domain.filter

import com.polestar.contextaware.data.model.FilteredLocation
import com.polestar.contextaware.data.model.RawLocation

/**
 * INTERFACE - STRATEGY PATTERN
 * This is the contract for the "neighbouring team".
 * We don't care how they implement it, just that they take raw data and return filtered data.
 */
interface LocationFilterAlgorithm {
    fun filter(raw: RawLocation): FilteredLocation
}