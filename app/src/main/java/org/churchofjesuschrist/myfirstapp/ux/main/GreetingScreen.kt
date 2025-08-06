package org.churchofjesuschrist.myfirstapp.ux.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.churchofjesuschrist.myfirstapp.ui.theme.MyFirstAppTheme

@Composable
fun GreetingScreen(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MyFirstAppTheme {
        GreetingScreen("Android")
    }
}
