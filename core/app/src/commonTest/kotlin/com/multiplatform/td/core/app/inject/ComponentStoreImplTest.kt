package com.multiplatform.td.core.app.inject

import dev.mokkery.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class ComponentStoreImplTest {

    private val componentStore: ComponentStore = ComponentStoreImpl()

    @Test
    fun `given store called will return new or already stored instance`() {
        val value: String = componentStore.store { "component-store" }

        assertEquals("component-store", value)
    }

    @Test
    fun `given remove called will return value in first call`() {
        componentStore.store { "component-store" }
        val value: String? = componentStore.remove()

        assertEquals("component-store", value)
        verify { componentStore.remove<String>() }
    }

    @Test
    fun `given remove called and there is no stored value will return null in first call`() {
        val value: String? = componentStore.remove()

        assertNull(value)
        verify { componentStore.remove<String>() }
    }
}
