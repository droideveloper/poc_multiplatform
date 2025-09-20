package com.multiplatform.td.core.ui.bottomcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.multiplatform.td.core.ui.TdTheme

enum class BottomCardButtonsOrientation {
    VERTICAL,
    HORIZONTAL,
}

interface BottomCardButtonsScope {

    var orientation: BottomCardButtonsOrientation

    @Composable
    fun Content()

    fun BottomCardButtonsScope.addButton(
        button: @Composable () -> Unit,
    )
}

internal class BottomCardButtonsScopeImpl : BottomCardButtonsScope {

    override var orientation: BottomCardButtonsOrientation = BottomCardButtonsOrientation.HORIZONTAL

    private val buttonsContent = mutableListOf<@Composable () -> Unit>()

    @Composable
    override fun Content() {
        when (orientation) {
            BottomCardButtonsOrientation.HORIZONTAL -> HorizontalButtonsContent()
            BottomCardButtonsOrientation.VERTICAL -> VerticalButtonsContent()
        }
    }

    @Composable
    private fun HorizontalButtonsContent() {
        Row(
            Modifier
                .fillMaxWidth()
                .testTag("buttons_horizontal_row"),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            buttonsContent.forEachIndexed { index, button ->
                Box(Modifier.weight(1f)) {
                    button()
                }
                if (index + 1 < buttonsContent.size) {
                    Spacer(
                        modifier = Modifier
                            .width(TdTheme.dimens.standard16),
                    )
                }
            }
        }
    }

    @Composable
    private fun VerticalButtonsContent() {
        Column(
            Modifier
                .fillMaxWidth()
                .testTag("buttons_vertical_column"),
        ) {
            buttonsContent.forEachIndexed { index, button ->
                button()
                if (index + 1 < buttonsContent.size) {
                    Spacer(
                        modifier = Modifier
                            .height(TdTheme.dimens.standard16),
                    )
                }
            }
        }
    }

    override fun BottomCardButtonsScope.addButton(button: @Composable () -> Unit) {
        buttonsContent.add(button)
    }
}
