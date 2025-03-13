package com.multiplatform.td.core.repository

sealed class CacheException : IllegalArgumentException() {

    data object Empty : CacheException()
    data object Expired : CacheException()
}
