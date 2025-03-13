package com.multiplatform.td.core.environment

sealed class OsVersion {

    data class AndroidVersion(
        val version: Int
    ) : Comparable<AndroidVersion>, OsVersion() {

        override fun compareTo(other: AndroidVersion): Int = version.compareTo(other.version)

        operator fun rangeTo(other: AndroidVersion) = AndroidVersionRange(this, other)

        class AndroidVersionRange(
            override val start: AndroidVersion,
            override val endInclusive: AndroidVersion,
        ) : ClosedRange<AndroidVersion>
    }

    data class IosVersion(val version: String): OsVersion()
}
