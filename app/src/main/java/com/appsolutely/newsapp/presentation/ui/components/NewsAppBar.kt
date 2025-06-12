package com.appsolutely.newsapp.presentation.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appsolutely.newsapp.presentation.ui.views.BottomRightSlashShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(
  modifier: Modifier = Modifier,
  title: String,
  isFullWidthAppBar: () -> Boolean,
  isHome: Boolean,
  onActionClick: () -> Unit
) {

  val cornerCut by animateFloatAsState(
    targetValue = if (isFullWidthAppBar()) 0f else 40f,
    animationSpec = tween(durationMillis = 600)
  )

  Surface(
    modifier = modifier
      .shadow(
        8.dp,
        shape = BottomRightSlashShape(cornerCut)
      )
  ) {
    TopAppBar(
      navigationIcon = {
        IconButton(onClick = onActionClick) {
          if (isHome) {
            Icon(
              Icons.Default.Menu,
              contentDescription = "Menu"
            )
          } else {
            Icon(
              Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Menu"
            )
          }
        }
      },
      title = {
        if (isHome) {
          if (isFullWidthAppBar()) {
            Box(
              modifier = Modifier.fillMaxWidth(),
              contentAlignment = Alignment.Center
            ) {
              Text(text = title)
            }
          } else {
            Text(
              modifier = Modifier.padding(start = 4.dp),
              text = "N"
            )
          }
        }
      },
      actions = {
        if (isFullWidthAppBar()) {
          IconButton(
            onClick = {}
          ) {
            Icon(Icons.Default.Search, contentDescription = "Search")
          }
        }
      },
      colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
  }
}