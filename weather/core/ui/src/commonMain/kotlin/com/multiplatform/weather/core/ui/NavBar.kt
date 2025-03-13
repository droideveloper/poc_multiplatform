package com.multiplatform.weather.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.multiplatform.td.core.ui.navbar.TdNavBar
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FwNavBar(
    modifier: Modifier = Modifier,
    title: NavBarDefaults.Title? = null,
    navAction: NavBarDefaults.Action? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TdNavBar(
        modifier = modifier,
        contentColor = Color.Transparent,
        title = title,
        navAction = navAction,
        actions = actions
    )
}

@Preview
@Composable
private fun FwNavBarPreview() {
    FwTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            FwNavBar(
                title = NavBarDefaults.Title.Default.Text("Hello World!"),
                navAction = NavBarDefaults.ArrowBackAction { },
            )
        }
    }
}
