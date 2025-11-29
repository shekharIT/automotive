package com.polestar.contextaware.data.model

import java.util.UUID

/**
 * Represents a specific data point found (e.g., a Charger).
 */
data class PointOfInterest(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: PlaceType,
    val distanceMeters: Int,
    val location: FilteredLocation
)
