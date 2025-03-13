package com.multiiplatform.td.core.database

class DatabaseName internal constructor(
    val name: String
) {
    val asFileName: String
        get() = "$name.db"

    companion object {

        val DefaultDatabase: DatabaseName = DatabaseName("default_database")
        fun of(name: String): DatabaseName = DatabaseName(name)
    }
}
