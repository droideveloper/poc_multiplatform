package com.multiplatform.td.core.ui.bottomcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdBottomCard(
    onClose: () -> Unit,
    isCloseVisible: Boolean = true,
    contentPadding: PaddingValues = selectBottomCardPadding(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = RoundedCornerShape(
            topStart = TdTheme.dimens.standard16,
            topEnd = TdTheme.dimens.standard16,
        ),
        modifier = Modifier.testTag("bottom_card"),
        colors = CardDefaults.cardColors(
            containerColor = TdTheme.colors.whites.primary,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AnimatedVisibility(
                visible = isCloseVisible,
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(
                            top = TdTheme.dimens.standard6,
                            end = TdTheme.dimens.standard6,
                        ),
                    onClick = { onClose() },
                ) {
                    Image(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                    )
                }
            }
            AnimatedVisibility(visible = !isCloseVisible) {
                Spacer(modifier = Modifier.height(TdTheme.dimens.standard20))
            }
            Column(
                modifier = Modifier.padding(contentPadding),
            ) {
                content()
            }
        }
    }
}

@Composable
internal fun selectBottomCardPadding() = PaddingValues(
    end = TdTheme.dimens.standard16,
    start = TdTheme.dimens.standard16,
    bottom = TdTheme.dimens.standard16,
)

@Preview
@Composable
private fun PreviewFmBottomCard() = TdTheme {
    TdBottomCard(onClose = { }) {
        Text(text = "Bottom card content.")
    }
}
