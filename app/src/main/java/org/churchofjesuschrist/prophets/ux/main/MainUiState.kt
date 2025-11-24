package org.churchofjesuschrist.prophets.ux.main

import kotlinx.coroutines.flow.StateFlow
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity

data class MainUiState(
    val prophetsFlow: StateFlow<List<ProphetEntity>>,
    val sortCriteriaFlow: StateFlow<SortCriteria>,
    val sortOrderFlow: StateFlow<SortOrder>,
    val onSortCriteriaChange: (SortCriteria) -> Unit = {},
    val onSortOrderChange: (SortOrder) -> Unit = {}
)
