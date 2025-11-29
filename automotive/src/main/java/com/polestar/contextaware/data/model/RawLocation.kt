package com.polestar.contextaware.data.model

/**
 * Represents raw GPS data coming from the HAL (Hardware Abstraction Layer).
 */
data class RawLocation(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val timestamp: Long = System.currentTimeMillis()
)
