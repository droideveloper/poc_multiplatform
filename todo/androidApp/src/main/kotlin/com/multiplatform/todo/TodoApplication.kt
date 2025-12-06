package com.multiplatform.todo

import android.app.Application
import android.os.StrictMode
import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.app.ComponentProvider
import com.multiplatform.td.core.app.create

open class TodoApplication : Application(), ComponentProvider {

    private val _component: AppComponent = AppComponent.create(this)
    override val component: AppComponent get() = _component

    override fun onCreate() {
        super.onCreate()
        val policy = StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .build()
        StrictMode.setThreadPolicy(policy)
    }
}
