package com.multiplatform.todo.tasks.inject.binder

import com.multiiplatform.td.core.database.DatabaseBuilderArgs
import com.multiiplatform.td.core.database.RoomDatabaseConfig
import com.multiiplatform.td.core.database.createDatabaseBuilder
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.todo.tasks.db.TodoDatabase
import me.tatarka.inject.annotations.Inject

@Inject
class TodoDatabaseBinder(
    private val roomDatabaseConfig: RoomDatabaseConfig,
    private val databaseBuilderArgs: DatabaseBuilderArgs,
) : Binder<TodoDatabase> {

    override fun invoke(): TodoDatabase {
        val builder = createDatabaseBuilder<TodoDatabase>(args = databaseBuilderArgs)
        return builder.apply(roomDatabaseConfig).build()
    }
}
