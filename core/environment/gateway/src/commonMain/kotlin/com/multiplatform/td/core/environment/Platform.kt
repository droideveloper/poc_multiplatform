package com.multiplatform.td.core.environment

sealed class Platform {

    data object Android : Platform() {
        override fun toString(): String = "Android"
    }

    data object Ios : Platform() {
        override fun toString(): String = "iOS"
    }
}

