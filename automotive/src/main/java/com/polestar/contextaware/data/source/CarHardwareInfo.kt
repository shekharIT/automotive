package com.polestar.contextaware.data.source

/**
 * Abstraction for the Car's hardware information.
 * In a real AAOS app, this would wrap `CarPropertyManager` or `CarInfoManager`.
 */
interface CarHardwareInfo {
    fun getModelName(): String
}