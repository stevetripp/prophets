package org.churchofjesuschrist.prophets.ux.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import org.churchofjesuschrist.prophets.ui.theme.ProphetsTheme
import org.churchofjesuschrist.prophets.ux.image.ImageRoute
import org.churchofjesuschrist.prophets.ux.image.ImageScreen
import org.churchofjesuschrist.prophets.ux.image.ImageViewModel
import org.churchofjesuschrist.prophets.ux.prophetdetail.ProphetDetailRoute
import org.churchofjesuschrist.prophets.ux.prophetdetail.ProphetDetailScreen
import org.churchofjesuschrist.prophets.ux.prophetdetail.ProphetDetailViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProphetsTheme {
                val backStack = rememberNavBackStack(MainRoute)

                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<MainRoute> {
                            MainScreen(
                                viewModel = hiltViewModel(),
                                onProphetClick = { prophetName -> backStack.add(ProphetDetailRoute(prophetName)) })
                        }
                        entry<ProphetDetailRoute> { key ->
                            ProphetDetailScreen(
                                viewModel = hiltViewModel<ProphetDetailViewModel, ProphetDetailViewModel.Factory>(creationCallback = { it.create(key) }),
                                onNavigateBack = { backStack.removeLastOrNull() }, onImageClick = { prophetName -> backStack.add(ImageRoute(prophetName)) })
                        }
                        entry<ImageRoute> { key ->
                            ImageScreen(onNavigateBack = { backStack.removeLastOrNull() }, viewModel = hiltViewModel<ImageViewModel, ImageViewModel.Factory>(creationCallback = { it.create(key) }))
                        }
                    },
                    entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator(), rememberViewModelStoreNavEntryDecorator()),
                )
            }
        }
    }
}
