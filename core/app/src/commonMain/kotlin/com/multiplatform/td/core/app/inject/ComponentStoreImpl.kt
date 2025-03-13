@file:Suppress("UNCHECKED_CAST")

package com.multiplatform.td.core.app.inject

import me.tatarka.inject.annotations.Inject
import kotlin.reflect.KClass

@Inject
class ComponentStoreImpl : ComponentStore {

    private val items = arrayListOf<Any>()

    override fun <T : Any> storeIfNotExists(clazz: KClass<T>, factory: () -> T): T =
        (items.firstOrNull { i -> clazz.isInstance(i) } as? T) ?: factory().also {
            items.add(it)
        }

    override fun <T : Any> removeIfExists(clazz: KClass<T>): T? {
        val component = (items.firstOrNull { a -> clazz.isInstance(a) } as? T)
        component?.let { items.remove(it) }
        return component
    }
}
