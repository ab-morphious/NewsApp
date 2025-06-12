package com.appsolutely.newsapp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsolutely.newsapp.R
import com.appsolutely.newsapp.R.string.full_article
import com.appsolutely.newsapp.domain.model.News
import com.appsolutely.newsapp.presentation.ui.components.NewsAppBar
import com.appsolutely.newsapp.presentation.viewmodel.NewsViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun NewsDetailScreen(
  id: Int,
  viewModel: NewsViewModel,
  onActionClick: () -> Unit
) {
  val news = viewModel.getNewsByUrl(id)

  Scaffold { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = paddingValues.calculateBottomPadding())
        .verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      NewsDetailImageWithNavBar(
        imageUrl = news?.imageUrl.orEmpty(),
        onActionClick = onActionClick
      )
      NewsDetailContent(news)
      Spacer(modifier = Modifier.weight(1f))
      NewsDetailActions()
    }
  }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsDetailImageWithNavBar(
  imageUrl: String,
  onActionClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(250.dp)
  ) {
    GlideImage(
      model = imageUrl,
      contentDescription = stringResource(R.string.news_detail_image),
      contentScale = ContentScale.Crop
    )
    NewsAppBar(
      modifier = Modifier.width(70.dp),
      title = "",
      isHome = false,
      isFullWidthAppBar = { false },
      onActionClick = onActionClick
    )
  }
}

@Composable
fun NewsDetailContent(news: News?) {
  Column(
    modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    Text(
      text = news?.author.orEmpty(),
      fontSize = 18.sp,
      color = Color.Black,
      fontWeight = FontWeight.W800
    )
    Text(
      text = news?.title.orEmpty(),
      fontSize = 24.sp,
      color = Color.Black,
      fontWeight = FontWeight.W600
    )
    Text(
      text = news?.content.orEmpty(),
      fontSize = 18.sp,
      color = Color.Black,
      fontWeight = FontWeight.Light
    )
  }
}

@Composable
fun NewsDetailActions() {
  Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    TextButton(onClick = { /* TODO: Implement share */ }) {
      Text(stringResource(R.string.share))
    }
    Button(
      colors = ButtonColors(
        contentColor = Color.White,
        containerColor = Color.Blue,
        disabledContentColor = Color.LightGray,
        disabledContainerColor = Color.DarkGray
      ),
      shape = RectangleShape,
      onClick = { /* TODO: Open full article */ }
    ) {
      Text(stringResource(full_article))
    }
  }
}
