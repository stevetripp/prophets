package org.churchofjesuschrist.myfirstapp.ux.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
    val name by uiState.name.collectAsState()
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFirstAppTheme {
        MainContent(uiState = MainUiState(name = MutableStateFlow("Preview")))
    }
}
