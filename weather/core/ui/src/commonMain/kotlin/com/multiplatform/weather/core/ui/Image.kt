package com.multiplatform.weather.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import com.multiplatform.td.core.ui.image.TdImage

@Composable
fun FwImage(
    resource: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null,
) {
    TdImage(
        resource = resource,
        modifier = modifier,
        contentDescription = contentDescription,
        contentScale = contentScale,
        colorFilter = colorFilter,
    )
}
