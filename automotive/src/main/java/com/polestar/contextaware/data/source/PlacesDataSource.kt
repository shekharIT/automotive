package com.polestar.contextaware.data.source

import com.polestar.contextaware.data.model.FilteredLocation
import com.polestar.contextaware.data.model.PlaceType
import com.polestar.contextaware.data.model.PointOfInterest

/**
 * Abstraction for the backend data (Points of Interest).
 */
interface PlacesDataSource {
    suspend fun getNearbyPlaces(location: FilteredLocation, type: PlaceType): List<PointOfInterest>
}