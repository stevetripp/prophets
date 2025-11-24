package org.churchofjesuschrist.prophets.ux.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import org.churchofjesuschrist.prophets.data.repository.ProphetRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    prophetRepository: ProphetRepository
) : ViewModel() {

    private val sortCriteriaFlow = MutableStateFlow(SortCriteria.DATE_CALLED)
    private val sortOrderFlow = MutableStateFlow(SortOrder.Ascending)
    private val prophetsFlow = prophetRepository.getProphetsFlow(viewModelScope)

    val prophetsStateFlow = combine(prophetsFlow, sortCriteriaFlow, sortOrderFlow) { prophets, criteria, order ->
        val sorted = when (criteria) {
            SortCriteria.DATE_CALLED -> {
                when (order) {
                    SortOrder.Ascending -> prophets.sortedBy { it.prophetCalled?.let { date -> LocalDate.parse(date) } }
                    SortOrder.Descending -> prophets.sortedByDescending { it.prophetCalled?.let { date -> LocalDate.parse(date) } }
                }
            }
            SortCriteria.AGE -> {
                when (order) {
                    SortOrder.Ascending -> prophets.sortedBy { it.getAgeAtDeathOrCurrent() }
                    SortOrder.Descending -> prophets.sortedByDescending { it.getAgeAtDeathOrCurrent() }
                }
            }
        }
        sorted
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uiState: MainUiState = MainUiState(
        prophetsFlow = prophetsStateFlow,
        sortCriteriaFlow = sortCriteriaFlow,
        sortOrderFlow = sortOrderFlow,
        onSortCriteriaChange = ::setSortCriteria,
        onSortOrderChange = ::setSortOrder
    )

    fun setSortCriteria(criteria: SortCriteria) {
        sortCriteriaFlow.value = criteria
    }

    fun setSortOrder(order: SortOrder) {
        sortOrderFlow.value = order
    }
}
