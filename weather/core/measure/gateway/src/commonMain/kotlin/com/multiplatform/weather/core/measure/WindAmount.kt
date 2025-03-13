package com.multiplatform.weather.core.measure

data class WindAmount(
    val amount: Double,
    val unit: Speed,
) {
    companion object {

        fun kmh(amount: Double): WindAmount =
            WindAmount(amount, Speed.KilometersPerHour)

        fun knots(amount: Double): WindAmount =
            WindAmount(amount, Speed.Knots)

        fun mph(amount: Double): WindAmount =
            WindAmount(amount, Speed.MilesPerHour)

        fun ms(amount: Double): WindAmount =
            WindAmount(amount, Speed.MetersPerSecond)
    }
}

fun WindAmount.toKilometersPerHour(): WindAmount {
    if (unit == Speed.KilometersPerHour) return this
    return WindAmount(
        amount = when (unit) {
            is Speed.MetersPerSecond -> amount * 3.6
            is Speed.Knots -> amount / 0.539957
            is Speed.MilesPerHour -> amount * 1.609344
            is Speed.KilometersPerHour -> amount
        },
        unit = Speed.KilometersPerHour,
    )
}
fun WindAmount.toMetersPerSecond(): WindAmount {
    if (unit == Speed.MetersPerSecond) return this
    val kmh = toKilometersPerHour()
    return WindAmount(
        amount = kmh.amount * 0.27777778,
        unit = Speed.MetersPerSecond,
    )
}

fun WindAmount.toMilesPerHour(): WindAmount {
    if (unit == Speed.MilesPerHour) return this
    val kmh = toKilometersPerHour()
    return WindAmount(
        amount = kmh.amount * 0.621371,
        unit = Speed.MilesPerHour,
    )
}
fun WindAmount.toKnots(): WindAmount {
    if (unit == Speed.Knots) return this
    val kmh = toKilometersPerHour()
    return WindAmount(
        amount = kmh.amount * 0.539957,
        unit = Speed.Knots,
    )
}