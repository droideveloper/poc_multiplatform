package com.multiplatform.todo

import android.app.Application
import android.os.StrictMode
import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.app.ComponentProvider
import com.multiplatform.td.core.app.create

class TodoApplication : Application(), ComponentProvider {

    override val component: AppComponent get() = AppComponent.create(this)

    override fun onCreate() {
        super.onCreate()
        val policy = StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .build()
        StrictMode.setThreadPolicy(policy)
    }
}
