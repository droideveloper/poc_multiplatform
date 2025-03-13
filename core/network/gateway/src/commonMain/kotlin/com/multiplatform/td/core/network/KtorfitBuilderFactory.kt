package com.multiplatform.td.core.network

import de.jensklingenberg.ktorfit.Ktorfit

interface KtorfitBuilderFactory {
    operator fun invoke(): Ktorfit.Builder
}