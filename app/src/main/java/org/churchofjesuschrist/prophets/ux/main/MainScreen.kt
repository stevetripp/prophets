package org.churchofjesuschrist.prophets.ux.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import org.churchofjesuschrist.prophets.R
import org.churchofjesuschrist.prophets.ui.preview.ProphetListPreviewParameterProvider
import org.churchofjesuschrist.prophets.ui.theme.ProphetsTheme
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onProphetClick: (String) -> Unit
) {
    val uiState = viewModel.uiState
    MainContent(uiState = uiState/*, modifier = modifier*/, onProphetClick = onProphetClick)
}

@Composable
private fun MainContent(
    uiState: MainUiState,
    onProphetClick: (String) -> Unit
) {
    val prophets by uiState.prophetsFlow.collectAsStateWithLifecycle()
    val sortCriteria by uiState.sortCriteriaFlow.collectAsStateWithLifecycle()
    val sortOrder by uiState.sortOrderFlow.collectAsStateWithLifecycle()
    val (showMenu, setShowMenu) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.latter_day_prophets)) },
                actions = {
                    IconButton(onClick = { setShowMenu(true) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Sort,
                            contentDescription = stringResource(id = R.string.sort_icon_desc),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    SortMenu(
                        showMenu = showMenu,
                        onDismiss = { setShowMenu(false) },
                        sortCriteria = sortCriteria,
                        sortOrder = sortOrder,
                        onSortCriteriaChange = { newCriteria ->
                            uiState.onSortCriteriaChange(newCriteria)
                            setShowMenu(false)
                        },
                        onSortOrderChange = { newOrder ->
                            uiState.onSortOrderChange(newOrder)
                            setShowMenu(false)
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(prophets) { prophet ->
                val age = prophet.getAgeAtDeathOrCurrent()
                ListItem(
                    headlineContent = { Text(text = "${prophet.name}${if (age > 0) " (age $age)" else ""}") },
                    leadingContent = {
                        AsyncImage(
                            model = prophet.imageUrl,
                            contentDescription = "Prophet image",
                            modifier = Modifier.size(56.dp)
                        )
                    },
                    supportingContent = {
                        val dateRange = formatProphetDateRange(prophet.prophetCalled, prophet.died)
                        Text(text = dateRange)
                    },
                    modifier = Modifier
                        .clickable { onProphetClick(prophet.name) }
                )
            }
        }
    }
}


private fun formatProphetDateRange(prophetCalledDate: String?, diedDate: String?): String {
    val formatter = DateTimeFormatter.ofPattern("MMM yyyy", Locale.getDefault())

    val calledStr = if (prophetCalledDate.isNullOrBlank()) {
        "Unknown"
    } else {
        LocalDate.parse(prophetCalledDate).toJavaLocalDate().format(formatter)
    }

    return if (diedDate.isNullOrBlank()) {
        // Still alive - show called date to present
        "$calledStr - Present"
    } else {
        // Deceased - show called date to death date
        val died = LocalDate.parse(diedDate).toJavaLocalDate()
        val diedStr = died.format(formatter)
        "$calledStr - $diedStr"
    }
}

@Composable
private fun SortMenu(
    showMenu: Boolean,
    onDismiss: () -> Unit,
    sortCriteria: SortCriteria,
    sortOrder: SortOrder,
    onSortCriteriaChange: (SortCriteria) -> Unit,
    onSortOrderChange: (SortOrder) -> Unit
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismiss
    ) {
        // Sort by criteria
        DropdownMenuItem(
            text = { Text("Date Called") },
            onClick = { onSortCriteriaChange(SortCriteria.DATE_CALLED) },
            trailingIcon = {
                if (sortCriteria == SortCriteria.DATE_CALLED) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Selected")
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Age") },
            onClick = { onSortCriteriaChange(SortCriteria.AGE) },
            trailingIcon = {
                if (sortCriteria == SortCriteria.AGE) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Selected")
                }
            }
        )
        // Separator
        HorizontalDivider()
        // Sort order
        DropdownMenuItem(
            text = { Text("Ascending") },
            onClick = { onSortOrderChange(SortOrder.Ascending) },
            trailingIcon = {
                if (sortOrder == SortOrder.Ascending) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Selected")
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Descending") },
            onClick = { onSortOrderChange(SortOrder.Descending) },
            trailingIcon = {
                if (sortOrder == SortOrder.Descending) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Selected")
                }
            }
        )
    }
}

@Preview()
@Composable
private fun Preview() {
    val prophets = ProphetListPreviewParameterProvider().values.toList()
    ProphetsTheme {
        MainContent(
            uiState = MainUiState(
                prophetsFlow = MutableStateFlow(prophets),
                sortCriteriaFlow = MutableStateFlow(SortCriteria.DATE_CALLED),
                sortOrderFlow = MutableStateFlow(SortOrder.Ascending),
            ),
            onProphetClick = {}
        )
    }
}
