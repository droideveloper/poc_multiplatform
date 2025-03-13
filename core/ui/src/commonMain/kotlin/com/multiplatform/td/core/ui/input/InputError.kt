package com.multiplatform.td.core.ui.input

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import tdmultiplatform.core.ui.generated.resources.Res
import tdmultiplatform.core.ui.generated.resources.core_ui_input_error_min_length_error
import tdmultiplatform.core.ui.generated.resources.core_ui_input_error_not_allowed
import tdmultiplatform.core.ui.generated.resources.core_ui_input_error_not_matching
import tdmultiplatform.core.ui.generated.resources.core_ui_input_error_required_value

sealed class InputError<V> {

    data object NotMatching : InputError<Nothing>()
    data object NotAllowed : InputError<Nothing>()
    data class MinLength(val atLeast: Int) : InputError<Nothing>()
    data class WithMessage(val string: String) : InputError<Nothing>()
    data class WithResMessage(val stringRes: StringResource) : InputError<Nothing>()
    data object Unspecified : InputError<Nothing>()
    data object None : InputError<Nothing>()
}

typealias TextError = InputError<Nothing>

fun <V> InputError<V>.isError() = this !is InputError.None

@Composable
fun <V> InputError<V>.selectErrorMessage() = when (this) {
    is InputError.NotMatching -> stringResource(Res.string.core_ui_input_error_not_matching)
    is InputError.NotAllowed -> stringResource(Res.string.core_ui_input_error_not_allowed)
    is InputError.MinLength -> stringResource(Res.string.core_ui_input_error_min_length_error, atLeast)
    is InputError.WithMessage -> string
    is InputError.WithResMessage -> stringResource(stringRes)
    InputError.Unspecified -> stringResource(Res.string.core_ui_input_error_required_value)
    InputError.None -> ""
}
