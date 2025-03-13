package com.multiplatform.td.core.network

data class HttpErrorException(
    val httpErrorCode: HttpErrorCode,
): IllegalStateException()

data class NoContentException(
    val noContentCode: NoContentCode,
): IllegalStateException()
