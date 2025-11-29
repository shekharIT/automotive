package com.polestar.contextaware.ui

import com.polestar.contextaware.data.model.PointOfInterest

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val places: List<PointOfInterest>, val algorithmUsed: String) : UiState()
    data class Error(val message: String) : UiState()
}