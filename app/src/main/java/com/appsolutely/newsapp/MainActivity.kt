package com.appsolutely.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.appsolutely.newsapp.presentation.ui.NewsDetailScreen
import com.appsolutely.newsapp.presentation.ui.NewsScreen
import com.appsolutely.newsapp.presentation.viewmodel.NewsViewModel
import com.appsolutely.newsapp.presentation.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                val viewModel: NewsViewModel = hiltViewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(navController, startDestination = "news_list") {
                        composable("news_list") {
                            NewsScreen(viewModel, navController)
                        }
                        composable(
                            route = "news_detail/{position}",
                            arguments = listOf(
                                navArgument("position") { type = NavType.IntType },
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("position") ?: -1
                            NewsDetailScreen(id, viewModel, navController)
                        }
                    }
                }
            }
        }
    }
}