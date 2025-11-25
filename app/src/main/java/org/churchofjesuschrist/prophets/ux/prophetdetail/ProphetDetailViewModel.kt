package org.churchofjesuschrist.prophets.ux.prophetdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import org.churchofjesuschrist.prophets.data.repository.ProphetRepository

@HiltViewModel(assistedFactory = ProphetDetailViewModel.Factory::class)
class ProphetDetailViewModel @AssistedInject constructor(
    prophetRepository: ProphetRepository,
    @Assisted prophetDetailRoute: ProphetDetailRoute
) : ViewModel() {

    private val prophetFlow = prophetRepository.getProphetByNameFlow(prophetDetailRoute.name)
        .stateIn(
            viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val uiState = ProphetDetailUiState(
        prophetFlow = prophetFlow,
    )

    @AssistedFactory
    interface Factory {
        fun create(route: ProphetDetailRoute): ProphetDetailViewModel
    }
}
