package org.churchofjesuschrist.myfirstapp.ux.main

import kotlinx.coroutines.flow.StateFlow
import org.churchofjesuschrist.myfirstapp.data.local.entity.ProphetEntity

data class MainUiState(
    val prophetsFlow: StateFlow<List<ProphetEntity>>
)
