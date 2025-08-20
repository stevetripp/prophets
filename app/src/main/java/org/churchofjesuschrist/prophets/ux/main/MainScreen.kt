package org.churchofjesuschrist.prophets.ux.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import org.churchofjesuschrist.prophets.R
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
                title = { Text(text = stringResource(id = R.string.latter_day_prophets)) }
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
                    modifier = Modifier
                        .clickable { onProphetClick(prophet.name) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProphetsTheme {
        MainContent(
            uiState = MainUiState(
                prophetsFlow = MutableStateFlow(emptyList())
            ),
            onProphetClick = {}
        )
    }
}
