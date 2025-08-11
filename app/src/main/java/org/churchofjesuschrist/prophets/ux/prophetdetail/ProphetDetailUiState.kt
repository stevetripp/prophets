package org.churchofjesuschrist.prophets.ux.prophetdetail

import kotlinx.coroutines.flow.StateFlow
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity

data class ProphetDetailUiState(
    val prophetFlow: StateFlow<ProphetEntity?>,
)
