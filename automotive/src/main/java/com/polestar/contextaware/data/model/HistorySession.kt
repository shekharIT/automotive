package com.polestar.contextaware.data.model

/**
 * A snapshot of history to be stored while the app is running.
 */
data class HistorySession(
    val timestamp: Long,
    val carModel: String,
    val originalLocation: RawLocation,
    val processedLocation: FilteredLocation,
    val placesFound: List<PointOfInterest>
)
