package com.polestar.contextaware.ui

import com.polestar.contextaware.data.model.HistorySession
import com.polestar.contextaware.data.repository.ContextAwareRepository
import com.polestar.contextaware.data.source.CarHardwareInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(
    private val repository: ContextAwareRepository,
    private val carHardwareInfo: CarHardwareInfo // Injected just to show UI which car we are
) {
    // Backing property for state
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _historyState = MutableStateFlow<List<HistorySession>>(emptyList())
    val historyState: StateFlow<List<HistorySession>> = _historyState.asStateFlow()

    /**
     * Triggered by user action (Explicit start).
     */
    suspend fun onUserRefreshedData() {
        _uiState.value = UiState.Loading

        // Simulate network/processing delay
        delay(500)

        val result = repository.refreshData()

        result.onSuccess { places ->
            val carName = carHardwareInfo.getModelName()
            _uiState.value = UiState.Success(places, "Filtered for $carName")
            // Update history view as well
            _historyState.value = repository.getHistory()
        }.onFailure { error ->
            _uiState.value = UiState.Error(error.localizedMessage ?: "Unknown Error")
        }
    }
}