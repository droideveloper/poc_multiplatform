package com.multiplatform.weather.core.measure

sealed interface Symbol

sealed interface Temperature : Symbol {

    data object Celsius : Temperature {
        override fun toString(): String = "°C"
    }

    data object Fahrenheit : Temperature {
        override fun toString(): String = "°F"
    }
}

sealed interface Pressure : Symbol {

    data object MeanSeaLevel : Pressure {
        override fun toString(): String = "hPa"
    }
}

sealed interface Humidity : Symbol {

    data object Percentage : Humidity {
        override fun toString(): String = "%"
    }
}

sealed interface UvIndex : Symbol {

    data object Default : UvIndex {
        override fun toString(): String = ""
    }
}

sealed interface Speed : Symbol {

    data object KilometersPerHour : Speed {
        override fun toString(): String = "km/h"
    }

    data object MetersPerSecond : Speed {
        override fun toString(): String = "m/s"
    }

    data object MilesPerHour : Speed {
        override fun toString(): String = "mph"
    }

    data object Knots : Speed {
        override fun toString(): String = "knots"
    }
}
