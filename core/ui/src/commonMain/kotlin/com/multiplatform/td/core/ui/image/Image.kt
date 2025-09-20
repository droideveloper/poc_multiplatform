package com.multiplatform.td.core.ui.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics

val DrawableResource = SemanticsPropertyKey<ImageVector>("ImageVector")
var SemanticsPropertyReceiver.drawableResource by DrawableResource

@Composable
fun TdImage(
    resource: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null,
) {
    Image(
        painter = rememberVectorPainter(resource),
        modifier = modifier.semantics { drawableResource = resource },
        contentDescription = contentDescription,
        contentScale = contentScale,
        colorFilter = colorFilter,
    )
}
