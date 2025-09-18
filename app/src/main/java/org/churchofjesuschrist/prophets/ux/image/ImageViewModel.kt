package org.churchofjesuschrist.prophets.ux.image

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.stateIn
import org.churchofjesuschrist.prophets.data.repository.ProphetRepository

@HiltViewModel
class ImageViewModel @Inject constructor(
    prophetRepository: ProphetRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ImageRoute>()
    private val prophetFlow = prophetRepository.getProphetByNameFlow(route.name)
        .stateIn(
            viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val uiState = ImageUiState(
        prophetFlow = prophetFlow,
    )
}
