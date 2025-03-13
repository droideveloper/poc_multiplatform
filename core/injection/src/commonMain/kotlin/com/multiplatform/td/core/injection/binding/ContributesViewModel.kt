package com.multiplatform.td.core.injection.binding

import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.reflect.KClass

@Target(CLASS)
@Repeatable
annotation class ContributesViewModel(
    /*
        will define scope on binder implementation if not specified it will remain as it is
     */
    val scope: KClass<*> = Unit::class,
    /*
        will provide additional info for binding boundType where you might want to provide specific one when you have multiple inheritance
     */
    val boundType: KClass<*> = Any::class,
    /*
        will use property style binding presented in KotlinInject otherwise will generate as method and take as argument instead of extension property
     */
    val useProperty: Boolean = false,
)
