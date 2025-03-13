package com.multiplatform.td.core.repository

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

sealed interface Criteria {

     interface Timed : Criteria {
         val maxAge: Duration
     }

    data object Fresh : Timed {
        override val maxAge: Duration
            get() = Duration.ZERO
    }

    data object Recent : Timed {
        override val maxAge: Duration
            get() = 5.minutes
    }

    data object Stale : Timed {
        override val maxAge: Duration
            get() = Duration.INFINITE
    }

    companion object {
        fun ofTimed(duration: Duration): Timed = object : Timed {
            override val maxAge: Duration
                get() = duration
        }
    }
}
