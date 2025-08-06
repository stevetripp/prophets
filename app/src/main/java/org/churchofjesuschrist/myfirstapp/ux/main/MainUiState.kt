package org.churchofjesuschrist.myfirstapp.ux.main

import kotlinx.coroutines.flow.MutableStateFlow

data class MainUiState(
    val name: MutableStateFlow<String>,
    val imageUrl: MutableStateFlow<String>
)
