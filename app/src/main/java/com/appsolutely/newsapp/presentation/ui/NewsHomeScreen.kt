package com.appsolutely.newsapp.presentation.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.appsolutely.newsapp.R
import com.appsolutely.newsapp.common.timeElapsed
import com.appsolutely.newsapp.domain.model.News
import com.appsolutely.newsapp.presentation.ui.components.NewsAppBar
import com.appsolutely.newsapp.presentation.ui.views.LineSeparator
import com.appsolutely.newsapp.presentation.viewmodel.NewsUiState
import com.appsolutely.newsapp.presentation.viewmodel.NewsViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun NewsScreen(viewModel: NewsViewModel, navController: NavHostController) {
    val newsState by viewModel.newsState.collectAsState()
    val lazyListState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(viewModel) {
        viewModel.getNews("tesla", "2025-02-08", "publishedAt")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (newsState) {
            is NewsUiState.Loading -> NewsLoading()
            is NewsUiState.Success -> {
                val newsList = (newsState as NewsUiState.Success).news
                NewsList(newsList, lazyListState, navController)
            }

            is NewsUiState.Error -> NewsError((newsState as NewsUiState.Error).message)
        }

        val appBarWidth by animateDpAsState(
            targetValue = if (lazyListState.firstVisibleItemScrollOffset > 0) 100.dp else screenWidth,
            label = ""
        )

        NewsAppBar(
            title = stringResource(R.string.front_page),
            lazyListState = lazyListState,
            appBarWidth = appBarWidth,
            isHome = true,
            onActionClick = {}
        )
    }
}

@Composable
fun NewsLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun NewsError(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = Color.Red, fontSize = 18.sp)
    }
}

@Composable
fun NewsList(newsList: List<News>, lazyListState: LazyListState, navController: NavHostController) {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(
                top =
                if (remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }.value == 0)
                    (statusBarHeight + 60.dp)
                else
                    statusBarHeight
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyListState
    ) {
        item { NewsHeader(newsList.first(), navController) }
        items(newsList.drop(1)) { news -> NewsItem(navController, news) }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsHeader(news: News, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable { navController.navigate("news_detail/${news.hashCode()}") },
    ) {
        GlideImage(
            modifier = Modifier.height(300.dp),
            model = news.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            text = news.publishedAt?.timeElapsed().orEmpty(), fontSize = 16.sp, color = Color.Gray
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            text = news.title.orEmpty(),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        LineSeparator()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsItem(navController: NavHostController, news: News) {
    Column(modifier = Modifier.clickable { navController.navigate("news_detail/${news.hashCode()}") }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = news.publishedAt.orEmpty().timeElapsed(),
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                Text(
                    text = news.title.orEmpty(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            GlideImage(
                modifier = Modifier.size(90.dp),
                model = news.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LineSeparator()
    }
}

