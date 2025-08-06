package org.churchofjesuschrist.myfirstapp.ux.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
    val name by uiState.name.collectAsState()
    val imageUrl by uiState.imageUrl.collectAsState()
     Column(modifier = modifier) {
        Text(
            text = "Hello $name!",
        )
        AsyncImage(
            model = imageUrl,
            contentDescription = "Sample image",
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyFirstAppTheme {
        MainContent(
            uiState = MainUiState(
                name = MutableStateFlow("Preview"),
                imageUrl = MutableStateFlow("https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=400&q=80")
            )
        )
    }
}
