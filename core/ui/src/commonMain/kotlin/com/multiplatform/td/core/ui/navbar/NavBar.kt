package com.multiplatform.td.core.ui.navbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.default
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TdNavBar(
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Unspecified,
    title: NavBarDefaults.Title? = null,
    navAction: NavBarDefaults.Action? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        colors = selectToolbarColor(contentColor),
        title = {
            title?.Content()
        },
        navigationIcon = {
            navAction?.Content()
        },
        actions = actions,
    )
}

@Composable
internal fun ToolbarTitle(
    text: String,
    style: TextStyle? = null
) {
    Text(
        text = text,
        style = style ?: TdTheme.typography.titleMedium,
        modifier = Modifier.testTag("nav_bar_title"),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
internal fun ToolbarSecondaryTitle(
    text: String,
    style: TextStyle? = null
) {
    Text(
        text = text,
        style = style ?: TdTheme.typography.titleSmallSemi,
        modifier = Modifier.testTag("nav_bar_secondary_title"),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun selectToolbarColor(color: Color) = TopAppBarDefaults.topAppBarColors().copy(
    containerColor = color.default(TdTheme.colors.whites.primary)
)

@Preview
@Composable
private fun TodoNavBarArrowPreview() {
    TdTheme {
        TdNavBar(
            modifier = Modifier.fillMaxWidth(),
            title = NavBarDefaults.Title.Default.Text("Title"),
            navAction = NavBarDefaults.ArrowBackAction {  },
        )
    }
}

@Preview
@Composable
private fun TodoNavBarEmptyPreview() {
    TdTheme {
        TdNavBar(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun TodoNavBarNoTitlePreview() {
    TdTheme {
        TdNavBar(
            modifier = Modifier.fillMaxWidth(),
            navAction = NavBarDefaults.CloseAction {  },
        )
    }
}

@Preview
@Composable
private fun TodoNavBarClosePreview() {
    TdTheme {
        TdNavBar(
            modifier = Modifier.fillMaxWidth(),
            title = NavBarDefaults.Title.Default.Text("Title"),
            navAction = NavBarDefaults.CloseAction {  },
        )
    }
}

@Preview
@Composable
private fun TodoNavBarCloseEndActionsPreview() {
    TdTheme {
        TdNavBar(
            modifier = Modifier.fillMaxWidth(),
            title = NavBarDefaults.Title.Secondary.Text(
                title = "Title",
                secondaryTitle = "Secondary Title",
            ),
            navAction = NavBarDefaults.CloseAction {  },
            actions = {
                NavBarDefaults.Action.Text("Settings") {  }.Content()
            }
        )
    }
}
