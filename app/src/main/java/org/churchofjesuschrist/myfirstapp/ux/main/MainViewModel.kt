package org.churchofjesuschrist.myfirstapp.ux.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val name = MutableStateFlow("Android")
    val imageUrl = MutableStateFlow("https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=400&q=80")
    val uiState: MainUiState = MainUiState(name = name, imageUrl = imageUrl)
}
