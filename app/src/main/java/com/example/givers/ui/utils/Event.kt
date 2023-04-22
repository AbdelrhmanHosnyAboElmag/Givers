package com.example.givers.ui.utils

/**
 * Event wrapper for live data values to avoid multiple reads
 */
class Event<out T>(private val value: T) {
    /**
     * Indicates that the event has been handled
     */
    private var mHandled = false

    /**
     * Get the event value if not handled or null otherwise
     */
    fun getValue(): T? {
        return if (mHandled)
            null
        else {
            mHandled = true
            value
        }
    }

    /**
     * Get the event value
     */
    fun getForcedValue(): T {
        return value
    }
}