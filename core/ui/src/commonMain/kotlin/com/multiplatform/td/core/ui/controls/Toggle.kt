package com.multiplatform.td.core.ui.controls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdToggle(
    checked: Boolean,
    modifier: Modifier = Modifier,
    stateColorProvider: @Composable (Boolean) -> Color = { selectCheckStateColor(it) },
    onCheckedChange: (Boolean) -> Unit = {},
) {
    val animatedHorizontalBias by animateFloatAsState(
        targetValue = if (checked) 1f else -1f,
    )
    val toggleAlignment by derivedStateOf {
        BiasAlignment(
            horizontalBias = animatedHorizontalBias,
            verticalBias = 0f,
        )
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .width(TdTheme.dimens.standard36)
            .background(color = stateColorProvider(checked))
            .toggleable(value = checked, role = Role.Switch) {
                onCheckedChange(it)
            }
            .then(modifier),
        contentAlignment = toggleAlignment,
    ) {
        Box(modifier = Modifier.padding(2.dp)) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .size(TdTheme.dimens.standard16)
                    .background(TdTheme.colors.whites.primary),
            )
        }
    }
}

@Composable
internal fun selectCheckStateColor(checked: Boolean) = when {
    checked -> TdTheme.colors.oranges.primary
    else -> TdTheme.colors.greys.primary
}

@Preview()
@Composable
private fun Checked() = TdTheme {
    TdToggle(checked = true)
}

@Preview()
@Composable
private fun NotChecked() = TdTheme {
    TdToggle(checked = false)
}
