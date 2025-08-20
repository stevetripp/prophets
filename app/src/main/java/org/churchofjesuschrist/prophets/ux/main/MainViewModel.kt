package org.churchofjesuschrist.prophets.ux.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import org.churchofjesuschrist.prophets.data.repository.ProphetRepository

@HiltViewModel
class MainViewModel @Inject constructor(
    prophetRepository: ProphetRepository
) : ViewModel() {

    private val sortOrderFlow = MutableStateFlow(SortOrder.Ascending)
    private val prophetsFlow = prophetRepository.getProphetsFlow(viewModelScope)

    val prophetsStateFlow = combine(prophetsFlow, sortOrderFlow) { prophets, sortOrder ->
        when (sortOrder) {
            SortOrder.Ascending -> prophets.sortedBy { prophet -> prophet.prophetCalled?.let { LocalDate.parse(it) } }
            SortOrder.Descending -> prophets.sortedByDescending { prophet -> prophet.prophetCalled?.let { LocalDate.parse(it) } }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uiState: MainUiState = MainUiState(
        prophetsFlow = prophetsStateFlow,
        onSortClick = ::toggleSortOrder
    )

    fun toggleSortOrder() {
        sortOrderFlow.value = when (sortOrderFlow.value) {
            SortOrder.Ascending -> SortOrder.Descending
            SortOrder.Descending -> SortOrder.Ascending
        }
    }
}
