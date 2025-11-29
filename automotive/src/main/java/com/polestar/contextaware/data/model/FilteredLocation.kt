package com.polestar.contextaware.data.model

/**
 * Represents the processed location after the "neighbouring team's" algorithm applied.
 */
data class FilteredLocation(
    val latitude: Double,
    val longitude: Double,
    val confidenceLevel: Float
)
