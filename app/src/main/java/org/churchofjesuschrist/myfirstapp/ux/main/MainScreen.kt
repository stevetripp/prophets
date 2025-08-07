package org.churchofjesuschrist.myfirstapp.ux.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import org.churchofjesuschrist.myfirstapp.ui.theme.MyFirstAppTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    MainContent(uiState = uiState, modifier = modifier)
}

@Composable
private fun MainContent(modifier: Modifier = Modifier, uiState: MainUiState) {
    val prophets by uiState.prophetsFlow.collectAsStateWithLifecycle()
    Scaffold { paddingValues ->
        LazyColumn(modifier = modifier.padding(paddingValues)) {
            items(prophets) { prophet ->
                ListItem(
                    headlineContent = { Text(text = prophet.name) },
                    leadingContent = {
                        AsyncImage(
                            model = prophet.imageUrl,
                            contentDescription = "Prophet image",
                            modifier = Modifier.size(56.dp)
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFirstAppTheme {
        MainContent(
            uiState = MainUiState(
                prophetsFlow = MutableStateFlow(emptyList())
            )
        )
    }
}
