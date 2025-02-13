package com.appsolutely.newsapp.presentation.ui.views

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class BottomRightSlashShape(private val cutSize: Float) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Generic(Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - cutSize) // Bottom-right cut start
            lineTo(size.width - cutSize, size.height) // Diagonal cut
            lineTo(0f, size.height)
            close()
        })
    }
}

