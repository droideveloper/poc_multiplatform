package com.multiplatform.todo.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.td.core.ui.navbar.TdNavBar as NavBar

@Composable
fun TdNavBar(
    title: String,
    secondaryTitle: String,
    navAction: NavBarDefaults.Action? = null,
    action: NavBarDefaults.Action? = null,
) {
    NavBar(
        navAction = navAction,
        title = NavBarDefaults.Title.Secondary.Text(
            title = title,
            titleStyle = TextStyle.Default.copy(
                fontSize = 21.sp,
                color = TdTheme.colors.blacks.secondary,
                fontWeight = FontWeight.Bold,
            ),
            secondaryTitle = secondaryTitle,
            secondaryTitleStyle = TextStyle.Default.copy(
                fontSize = 14.sp,
                color = TdTheme.colors.blacks.light,
                fontWeight = FontWeight.SemiBold,
            ),
        ),
        actions = { action?.Content() },
    )
}
