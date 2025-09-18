package org.churchofjesuschrist.prophets.ux.image

import kotlinx.serialization.Serializable

@Serializable
data class ImageRoute(val name: String) {
    companion object {
        private const val ROUTE_BASE = "image"
        const val ARG_NAME = "name"
        const val ROUTE_PATTERN = "$ROUTE_BASE/{$ARG_NAME}"

        fun createRoute(name: String) = "$ROUTE_BASE/$name"
    }
}
