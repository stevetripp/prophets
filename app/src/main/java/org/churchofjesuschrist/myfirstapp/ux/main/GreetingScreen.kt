package org.churchofjesuschrist.myfirstapp.ux.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.churchofjesuschrist.myfirstapp.ui.theme.MyFirstAppTheme
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GreetingScreen(
    viewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello ${viewModel.name}!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MyFirstAppTheme {
        GreetingScreen()
    }
}
