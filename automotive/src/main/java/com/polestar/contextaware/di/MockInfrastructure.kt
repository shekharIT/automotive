package com.polestar.contextaware.di

import com.polestar.contextaware.data.model.FilteredLocation
import com.polestar.contextaware.data.model.PlaceType
import com.polestar.contextaware.data.model.PointOfInterest
import com.polestar.contextaware.data.model.RawLocation
import com.polestar.contextaware.data.source.CarHardwareInfo
import com.polestar.contextaware.data.source.LocationProvider
import com.polestar.contextaware.data.source.PlacesDataSource

/**
 * =================================================================================
 * MOCK INFRASTRUCTURE IMPLEMENTATIONS
 * These classes satisfy the interfaces defined in the data/source package and are
 * used to run the application simulation without relying on actual hardware or APIs.
 * This file is part of the Dependency Injection (DI) layer in the simulation.
 * =================================================================================
 */

/**
 * MOCK IMPLEMENTATION: Simulates the Car's hardware manager (e.g., CarInfoManager in AAOS).
 * Implements the [CarHardwareInfo] interface.
 * @param simulatedModel The model string to return (e.g., "Polestar 2").
 */
class MockCarHardware(private val simulatedModel: String) : CarHardwareInfo {
    override fun getModelName() = simulatedModel
}

/**
 * MOCK IMPLEMENTATION: Simulates the Location API interface (e.g., FusedLocationProvider).
 * Implements the [LocationProvider] interface.
 */
class MockLocationProvider : LocationProvider {
    /**
     * Simulates receiving raw, uncorrected location data from GPS hardware.
     */
    override suspend fun getCurrentLocation(): RawLocation {
        // ASSUMPTION: Returning a fixed, raw location (Gothenburg HQ) with fixed accuracy.
        return RawLocation(latitude = 57.7089, longitude = 11.9746, accuracy = 10.0f)
    }
}

/**
 * MOCK IMPLEMENTATION: Simulates the external POI API endpoint.
 * Implements the [PlacesDataSource] interface.
 */
class MockPlacesApi : PlacesDataSource {
    /**
     * Simulates calling a backend service to find nearby POIs using the filtered location.
     */
    override suspend fun getNearbyPlaces(location: FilteredLocation, type: PlaceType): List<PointOfInterest> {
        // Return consistent mock data based on the type requested
        return when (type) {
            PlaceType.CHARGING_STATION -> listOf(
                // POI coordinates are set to match the filtered location passed in,
                PointOfInterest(
                    name = "North Shore DC Charger",
                    type = type,
                    distanceMeters = 300,
                    location = location
                ),
                PointOfInterest(
                    name = "Central Service Area AC",
                    type = type,
                    distanceMeters = 1500,
                    location = location
                )
            )
            // Other POI types would be mocked here if supported
            else -> emptyList()
        }
    }
}