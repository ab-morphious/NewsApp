package com.appsolutely.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.appsolutely.newsapp.presentation.ui.NewsDetailScreen
import com.appsolutely.newsapp.presentation.ui.NewsScreen
import com.appsolutely.newsapp.presentation.ui.theme.NewsAppTheme
import com.appsolutely.newsapp.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      NewsAppTheme {
        AppRoot()
      }
    }
  }

  @Composable
  @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
  private fun AppRoot() {
    val navController = rememberNavController()
    val viewModel: NewsViewModel = hiltViewModel()
    Scaffold(
      modifier = Modifier.fillMaxSize()
    ) {
      NavHost(
        navController = navController,
        startDestination = NewsListDestination
      ) {
        composable<NewsListDestination> {
          NewsScreen(
            viewModel = viewModel,
            onNewsItemSelected = { news ->
              val destination = NewsDetailsDestination(
                position = news.hashCode()
              )
              navController.navigate(destination)
            }
          )
        }
        composable<NewsDetailsDestination> { backStackEntry ->
          val destination = backStackEntry.toRoute<NewsDetailsDestination>()
          NewsDetailScreen(
            id = destination.position,
            viewModel = viewModel,
            onActionClick = { navController.navigateUp() }
          )
        }
      }
    }
  }
}

@Serializable
data object NewsListDestination

@Serializable
data class NewsDetailsDestination(
  val position: Int
)

