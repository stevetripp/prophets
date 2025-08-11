package org.churchofjesuschrist.myfirstapp.ux.prophetdetail

import kotlinx.coroutines.flow.StateFlow
import org.churchofjesuschrist.myfirstapp.data.local.entity.ProphetEntity

data class ProphetDetailUiState(
    val prophetFlow: StateFlow<ProphetEntity?>,
)
