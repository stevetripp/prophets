package org.churchofjesuschrist.prophets.ux.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.churchofjesuschrist.prophets.data.repository.ProphetRepository
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

@HiltViewModel
class MainViewModel @Inject constructor(
    prophetRepository: ProphetRepository
) : ViewModel() {
    private val prophetsFlow = prophetRepository.getProphetsFlow(viewModelScope)

    val prophetsStateFlow = prophetsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uiState: MainUiState = MainUiState(prophetsFlow = prophetsStateFlow)
}
