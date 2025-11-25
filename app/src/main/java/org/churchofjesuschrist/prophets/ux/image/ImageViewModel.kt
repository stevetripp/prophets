package org.churchofjesuschrist.prophets.ux.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import org.churchofjesuschrist.prophets.data.repository.ProphetRepository

@HiltViewModel(assistedFactory = ImageViewModel.Factory::class)
class ImageViewModel @AssistedInject constructor(
    prophetRepository: ProphetRepository,
    @Assisted imageRoute: ImageRoute,
) : ViewModel() {

    private val prophetFlow = prophetRepository.getProphetByNameFlow(imageRoute.name)
        .stateIn(
            viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val uiState = ImageUiState(
        prophetFlow = prophetFlow,
    )

    @AssistedFactory
    interface Factory {
        fun create(route: ImageRoute): ImageViewModel
    }
}
