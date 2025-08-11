package org.churchofjesuschrist.myfirstapp.ux.prophetdetail

import kotlinx.serialization.Serializable

@Serializable
data class ProphetDetailRoute(val name: String) {
    companion object {
        private const val ROUTE_BASE = "prophetDetail"
        const val ARG_NAME = "name"
        const val ROUTE_PATTERN = "$ROUTE_BASE/{$ARG_NAME}"

        fun createRoute(name: String) = "$ROUTE_BASE/$name"
    }
}
