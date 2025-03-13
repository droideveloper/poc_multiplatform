package com.multiplatform.td.core.ui.input

sealed class InputValue<V> {
    abstract val value: V

    data class Default<V>(override val value: V): InputValue<V>()
    data class Entered<V>(override val value: V): InputValue<V>()
}

typealias TextValue = InputValue<String>

fun <I, O> InputValue<I>.map(block: (I) -> O): InputValue<O> =
    when (this) {
        is InputValue.Entered -> InputValue.Entered(block(value))
        is InputValue.Default -> InputValue.Default(block(value))
    }

fun TextValue.hasOnlyDigits(): Boolean =
    value.all { it.isDigit() }

fun TextValue.isDecimal(): Boolean = value.toDoubleOrNull() != null

fun TextValue.hasNoDigits(): Boolean =
    value.hasNoDigits()

fun TextValue.hasLength(length: Int): Boolean = value.length == length

fun String.hasNoDigits(): Boolean =
    all { it.isDigit().not() }

fun TextValue.violatesRequired(): Boolean =
    value.violatesRequired()

fun TextValue.notViolatesRequired(): Boolean =
    !value.violatesRequired()

fun String.violatesRequired(): Boolean =
    isBlank()

fun <V> InputValue<V>.isEntered(): Boolean =
    this is InputValue.Entered

fun <V> InputValue<V>.isDefault(): Boolean =
    this is InputValue.Default