package org.churchofjesuschrist.myfirstapp.ux.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.churchofjesuschrist.myfirstapp.webservice.LocalCdn
import org.churchofjesuschrist.myfirstapp.webservice.dto.ProphetDto

@HiltViewModel
class MainViewModel @Inject constructor(
    localCdn: LocalCdn
) : ViewModel() {
    val prophetsFlow = MutableStateFlow(emptyList<ProphetDto>())

    val uiState: MainUiState = MainUiState(prophetsFlow = prophetsFlow)

    init {
        viewModelScope.launch {
            prophetsFlow.value = localCdn.getProphets()
        }
    }
}
