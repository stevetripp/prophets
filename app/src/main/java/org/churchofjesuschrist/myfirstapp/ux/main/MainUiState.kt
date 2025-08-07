package org.churchofjesuschrist.myfirstapp.ux.main

import kotlinx.coroutines.flow.MutableStateFlow
import org.churchofjesuschrist.myfirstapp.webservice.dto.ProphetDto

data class MainUiState(
    val prophetsFlow: MutableStateFlow<List<ProphetDto>>
)
