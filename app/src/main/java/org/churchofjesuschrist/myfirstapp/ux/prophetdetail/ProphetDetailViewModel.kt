package org.churchofjesuschrist.myfirstapp.ux.prophetdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.stateIn
import org.churchofjesuschrist.myfirstapp.data.repository.ProphetRepository

@HiltViewModel
class ProphetDetailViewModel @Inject constructor(
    prophetRepository: ProphetRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ProphetDetailRoute>()
    private val prophetFlow = prophetRepository.getProphetByNameFlow(route.name)
        .stateIn(
            viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val uiState = ProphetDetailUiState(
        prophetFlow = prophetFlow,
    )
}
