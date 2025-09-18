package org.churchofjesuschrist.prophets.ux.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import org.churchofjesuschrist.prophets.R
import org.churchofjesuschrist.prophets.ui.preview.ProphetListPreviewParameterProvider
import org.churchofjesuschrist.prophets.ui.theme.ProphetsTheme

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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.latter_day_prophets)) },
                actions = {
                    IconButton(onClick = uiState.onSortClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Sort,
                            contentDescription = stringResource(id = R.string.sort_icon_desc),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(prophets) { prophet ->
                ListItem(
                    headlineContent = { Text(text = prophet.name) },
                    leadingContent = {
                        AsyncImage(
                            model = prophet.imageUrl,
                            contentDescription = "Prophet image",
                            modifier = Modifier.size(56.dp)
                        )
                    },
                    supportingContent = {
                        val called = formatMonthYear(prophet.prophetCalled)
                        val died = formatMonthYear(prophet.died)
                        Text(text = "$called - $died")
                    },
                    modifier = Modifier
                        .clickable { onProphetClick(prophet.name) }
                )
            }
        }
    }
}

private fun formatMonthYear(dateString: String?): String {
    return if (dateString.isNullOrBlank()) "Unknown"
    else LocalDate.parse(dateString).toJavaLocalDate().format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.getDefault()))
}

@Preview()
@Composable
private fun Preview() {
    val prophets = ProphetListPreviewParameterProvider().values.toList()
    ProphetsTheme {
        MainContent(
            uiState = MainUiState(
                prophetsFlow = MutableStateFlow(prophets)
            ),
            onProphetClick = {}
        )
    }
}
