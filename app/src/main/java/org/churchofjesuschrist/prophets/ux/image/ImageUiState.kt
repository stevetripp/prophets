package org.churchofjesuschrist.prophets.ux.image

import kotlinx.coroutines.flow.StateFlow
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity

data class ImageUiState(
    val prophetFlow: StateFlow<ProphetEntity?>
)
