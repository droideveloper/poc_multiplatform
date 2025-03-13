package com.multiplatform.td.core.navigation

class FeatureNavOptions internal constructor(
    val singleTop: Boolean,
    val restoreState: Boolean,
    val inclusive: Boolean,
    val saveState: Boolean,
    val popUpTo: Any?,
) {

    data class Builder(
        internal var singleTop: Boolean = false,
        internal var restoreState: Boolean = false,
        internal var inclusive: Boolean = false,
        internal var saveState: Boolean = false,
        internal var popUpTo: Any? = null,
    ) {

        fun singleTop(singleTop: Boolean): Builder = apply { this.singleTop = singleTop }
        fun restoreState(restoreState: Boolean): Builder = apply { this.restoreState = restoreState }
        fun inclusive(inclusive: Boolean): Builder = apply { this.inclusive = inclusive }
        fun saveState(saveState: Boolean): Builder = apply { this.saveState = saveState }
        fun popUpTo(popUpTo: Any?): Builder = apply { this.popUpTo = popUpTo }

        fun build() = FeatureNavOptions(
            singleTop = singleTop,
            restoreState = restoreState,
            inclusive = inclusive,
            saveState = saveState,
            popUpTo = popUpTo,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other === this) return true
        if (other is FeatureNavOptions) {
            return other.singleTop == singleTop
                    && other.restoreState == restoreState
                    && other.inclusive == inclusive
                    && other.saveState == saveState
                    && other.popUpTo == popUpTo
        }
        return false
    }

    override fun hashCode(): Int {
        var result = singleTop.hashCode()
        result = 31 * result + restoreState.hashCode()
        result = 31 * result + inclusive.hashCode()
        result = 31 * result + saveState.hashCode()
        result = 31 * result + (popUpTo?.hashCode() ?: 0)
        return result
    }
}
