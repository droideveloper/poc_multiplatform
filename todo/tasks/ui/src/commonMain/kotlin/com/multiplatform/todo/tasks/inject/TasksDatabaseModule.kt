package com.multiplatform.todo.tasks.inject

import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.todo.tasks.db.TodoDatabase
import com.multiplatform.todo.tasks.inject.binder.TodoDatabaseBinder
import me.tatarka.inject.annotations.Provides

interface TasksDatabaseModule : TasksDataModule {

    @FeatureScope
    @Provides
    fun bindTodoDatabase(binder: TodoDatabaseBinder): TodoDatabase = binder()
}
