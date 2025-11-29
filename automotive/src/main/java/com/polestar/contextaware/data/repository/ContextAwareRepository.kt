package com.polestar.contextaware.data.repository

import com.polestar.contextaware.data.model.HistorySession
import com.polestar.contextaware.data.model.PlaceType
import com.polestar.contextaware.data.model.PointOfInterest
import com.polestar.contextaware.data.source.CarHardwareInfo
import com.polestar.contextaware.data.source.LocationProvider
import com.polestar.contextaware.data.source.PlacesDataSource
import com.polestar.contextaware.domain.filter.GenericFilter
import com.polestar.contextaware.domain.filter.LocationFilterAlgorithm

class ContextAwareRepository(
    private val locationProvider: LocationProvider,
    private val carHardwareInfo: CarHardwareInfo,
    private val placesDataSource: PlacesDataSource,
    // We inject a map of strategies. Key = Car Model Name.
    private val filterStrategies: Map<String, LocationFilterAlgorithm>
) {
    // "The application do only need to store this data when it is running though."
    // We use an in-memory MutableList. If persistence was needed across reboots, we'd use Room (SQLite).
    private val _sessionHistory = mutableListOf<HistorySession>()

    /**
     * The core use case:
     * 1. Get Hardware Info
     * 2. Select Algorithm (Strategy)
     * 3. Get Raw Location
     * 4. Apply Filter
     * 5. Fetch Data
     * 6. Save History
     */
    suspend fun refreshData(): Result<List<PointOfInterest>> {
        return try {
            // 1. Identify Car Model
            val modelName = carHardwareInfo.getModelName()

            // 2. Select Strategy (Default to Generic if model not found)
            val algorithm = filterStrategies[modelName] ?: GenericFilter()

            // 3. Get Raw Data
            val rawLocation = locationProvider.getCurrentLocation()

            // 4. Apply Neighbouring Team's Logic
            val filteredLocation = algorithm.filter(rawLocation)

            // 5. Fetch relevant POIs (Simulating fetching Charging Stations for this example)
            val pois = placesDataSource.getNearbyPlaces(filteredLocation, PlaceType.CHARGING_STATION)

            // 6. Store in Session History
            val historyEntry = HistorySession(
                timestamp = System.currentTimeMillis(),
                originalLocation = rawLocation,
                processedLocation = filteredLocation,
                placesFound = pois
            )
            synchronized(_sessionHistory) {
                _sessionHistory.add(historyEntry)
            }

            Result.success(pois)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getHistory(): List<HistorySession> {
        return synchronized(_sessionHistory) { _sessionHistory.toList() }
    }
}