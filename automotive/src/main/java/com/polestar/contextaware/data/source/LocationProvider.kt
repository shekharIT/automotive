package com.polestar.contextaware.data.source

import com.polestar.contextaware.data.model.RawLocation

/**
 * Abstraction for fetching raw location.
 * In a real app, this wraps `LocationManager` or FusedLocationProvider.
 */
interface LocationProvider {
    suspend fun getCurrentLocation(): RawLocation
}