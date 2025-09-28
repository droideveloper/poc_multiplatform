package com.multiplatform.td.core.ui

import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(
    CLASS,
    FUNCTION,
)
annotation class KoverIgnore()
