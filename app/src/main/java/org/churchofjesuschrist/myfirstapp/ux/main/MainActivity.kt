package org.churchofjesuschrist.myfirstapp.ux.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import org.churchofjesuschrist.myfirstapp.ui.theme.MyFirstAppTheme
import org.churchofjesuschrist.myfirstapp.ux.prophetdetail.ProphetDetailRoute
import org.churchofjesuschrist.myfirstapp.ux.prophetdetail.ProphetDetailScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        MainScreen(
                            onProphetClick = { prophetName ->
                                navController.navigate(
                                    ProphetDetailRoute.createRoute(prophetName)
                                )
                            }
                        )
                    }
                    composable(
                        route = ProphetDetailRoute.ROUTE_PATTERN,
                        arguments = listOf(
                            navArgument(ProphetDetailRoute.ARG_NAME) {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        ProphetDetailScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
