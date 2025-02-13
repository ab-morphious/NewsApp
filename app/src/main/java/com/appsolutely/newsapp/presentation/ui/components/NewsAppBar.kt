package com.appsolutely.newsapp.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appsolutely.newsapp.presentation.ui.views.BottomRightSlashShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(
    title: String, lazyListState: LazyListState?,
    appBarWidth: Dp,
    isHome: Boolean,
    onActionClick: () -> Unit
) {
    val isFullWidthAppBar by remember { derivedStateOf { lazyListState?.firstVisibleItemScrollOffset == 0 } }

    Surface(
        modifier = Modifier
            .width(appBarWidth)
            .shadow(
                8.dp,
                shape = if (isFullWidthAppBar) BottomRightSlashShape(0f) else BottomRightSlashShape(
                    40f
                )
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
                            Icons.Default.ArrowBack,
                            contentDescription = "Menu"
                        )
                    }
                }
            },
            title = {
                if (isHome) {
                    if (isFullWidthAppBar) {
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
                if (isFullWidthAppBar) {
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