package com.appsolutely.newsapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.appsolutely.newsapp.R

val merriweather = FontFamily(
    Font(R.font.merriweather_bold, FontWeight.Bold),
    Font(R.font.merriweather_regular, FontWeight.Normal),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = merriweather,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = merriweather,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
)
