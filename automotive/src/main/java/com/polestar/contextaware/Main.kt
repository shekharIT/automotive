package com.polestar.contextaware

import com.polestar.contextaware.data.repository.ContextAwareRepository
import com.polestar.contextaware.domain.filter.Polestar2Filter
import com.polestar.contextaware.domain.filter.Polestar3Filter
import com.polestar.contextaware.ui.DashboardViewModel
import com.polestar.contextaware.ui.UiState
import com.polestar.contextaware.di.MockCarHardware
import com.polestar.contextaware.di.MockLocationProvider
import com.polestar.contextaware.di.MockPlacesApi
import kotlinx.coroutines.runBlocking
import java.util.Date

/**
 * MAIN APPLICATION ENTRY POINT
 * This file handles the manual dependency wiring (simulating a DI framework like Hilt/Koin)
 * and executes the core logic flow within a coroutine scope.
 */
fun main() = runBlocking {
    println("--- Context-Aware AAOS App Simulation Starting (Kotlin) ---")

    // 1. DEPENDENCY SETUP (Manual DI)
    // We can easily change this to "Polestar 3" to test the different filtering strategy
    val simulatedCarModel = "Polestar 2"

    // Injecting the mock implementations
    val carHardware = MockCarHardware(simulatedCarModel)
    val locationProvider = MockLocationProvider()
    val placesApi = MockPlacesApi()

    // 2. FILTER STRATEGY REGISTRY
    val filterStrategies = mapOf(
        "Polestar 2" to Polestar2Filter(),
        "Polestar 3" to Polestar3Filter()
    )

    // 3. REPOSITORY INSTANTIATION
    val repository = ContextAwareRepository(
        locationProvider = locationProvider,
        carHardwareInfo = carHardware,
        placesDataSource = placesApi,
        filterStrategies = filterStrategies
    )

    // 4. VIEW MODEL INSTANTIATION
    val viewModel = DashboardViewModel(repository, carHardware)

    // =================================================================
    // SIMULATE USER INTERACTION FLOW (Explicit start and refresh)
    // =================================================================

    println("\n--- SIMULATION RUN 1: Car Model: $simulatedCarModel | User explicitly starts app ---")
    viewModel.onUserRefreshedData()

    when (val state = viewModel.uiState.value) {
        is UiState.Success -> {
            println("Status: SUCCESS | Algorithm Used: ${state.algorithmUsed}")
            state.places.forEach {
                println("  - POI: ${it.name} (Type: ${it.type}) @ ${it.distanceMeters}m")
            }
        }
        is UiState.Error -> {
            println("Status: ERROR | ${state.message}")
        }
        is UiState.Loading -> println("Status: Loading...")
        is UiState.Idle -> println("Status: Idle.")
    }

    // SIMULATE A SECOND REFRESH to demonstrate history storage persistence
    println("\n--- SIMULATION RUN 2: User refreshes data ---")
    viewModel.onUserRefreshedData()

    // =================================================================
    // CHECK HISTORY (In-memory storage)
    // =================================================================

    println("\n--- SESSION HISTORY CHECK (${Date()}) ---")
    val history = viewModel.historyState.value
    println("Total history entries stored: ${history.size}")

    history.forEachIndexed { index, session ->
        println("Entry ${index + 1} (${session.carModel}):")
        println("  Raw Loc: ${session.originalLocation.latitude}, ${session.originalLocation.longitude}")
        println("  Filtered Loc: ${session.processedLocation.latitude}, ${session.processedLocation.longitude} (Confidence: ${session.processedLocation.confidenceLevel})")
        println("  POIs Found: ${session.placesFound.joinToString { it.name }}")
    }
}