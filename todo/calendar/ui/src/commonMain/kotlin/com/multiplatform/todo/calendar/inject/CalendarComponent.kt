package com.multiplatform.todo.calendar.inject

import com.multiiplatform.td.core.database.inject.DatabaseComponent
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.navigation.inject.NavigationComponent
import com.multiplatform.todo.calendar.GeneratedViewModelModule
import com.multiplatform.todo.tasks.inject.TasksDatabaseModule
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

@FeatureScope
@Component
abstract class CalendarComponent(
    @Component val databaseComponent: DatabaseComponent,
    @Component val navigationComponent: NavigationComponent,
) : GeneratedViewModelModule(), TasksDatabaseModule {
    companion object;
}

@Suppress("KotlinNoActualForExpect")
@KmpComponentCreate
expect fun createCalendarComponent(
    databaseComponent: DatabaseComponent,
    navigationComponent: NavigationComponent,
): CalendarComponent
