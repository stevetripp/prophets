package org.churchofjesuschrist.myfirstapp.ux.prophetdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import org.churchofjesuschrist.myfirstapp.data.local.entity.ProphetEntity

@HiltViewModel
class ProphetDetailViewModel @Inject constructor() : ViewModel() {
    private val prophetFlow = MutableStateFlow<ProphetEntity?>(null)
        .stateIn(
            viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val uiState = ProphetDetailUiState(
        prophetFlow = prophetFlow,
    )
}
