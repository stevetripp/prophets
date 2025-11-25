package org.churchofjesuschrist.prophets.ux.prophetdetail

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class ProphetDetailRoute(val name: String) : NavKey