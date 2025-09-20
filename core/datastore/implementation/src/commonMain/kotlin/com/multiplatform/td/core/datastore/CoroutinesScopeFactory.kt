package com.multiplatform.td.core.datastore

import kotlinx.coroutines.CoroutineScope

interface CoroutinesScopeFactory {

    operator fun invoke(): CoroutineScope
}
