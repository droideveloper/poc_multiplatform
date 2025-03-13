package com.multiplatform.td.core.injection.scopes

import me.tatarka.inject.annotations.Scope
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER

@Scope
@Target(
    CLASS,
    FUNCTION,
    PROPERTY_GETTER,
    VALUE_PARAMETER,
)
annotation class DataStoreScope
