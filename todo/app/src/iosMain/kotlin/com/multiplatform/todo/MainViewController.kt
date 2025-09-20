package com.multiplatform.todo

import androidx.compose.ui.window.ComposeUIViewController
import com.multiplatform.td.core.app.createAppComponent

fun mainViewController() = ComposeUIViewController {
    TodoApp(
        component = createAppComponent(),
    )
}
