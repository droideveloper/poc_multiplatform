package com.multiplatform.todo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.multiiplatform.td.core.database.DatabaseName
import com.multiiplatform.td.core.database.composable.DatabaseContext
import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.app.composable.AppContext
import com.multiplatform.td.core.datastore.composable.DataStoreContext
import com.multiplatform.td.core.navigation.composable.NavigationContext
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.todo.home.homeGraph
import com.multiplatform.todo.tasks.Route

@Composable
fun TodoApp(
    component: AppComponent,
) {
    val navController = rememberNavController()

    AppContext(component, navController) {
        DatabaseContext(
            databaseName = DatabaseName.of("todo_database")
        ) {
            DataStoreContext {
                NavigationContext {
                    TdTheme {
                        NavHost(navController, startDestination = Route.Graph) {
                            homeGraph()
                        }
                    }
                }
            }
        }
    }
}