package com.multiplatform.td.core.ui.bottomcard

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.image.TdImage

@Composable
fun BottomCardIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
) {
    TdImage(
        modifier = Modifier
            .size(TdTheme.dimens.standard64)
            .then(modifier),
        resource = icon,
    )
}
