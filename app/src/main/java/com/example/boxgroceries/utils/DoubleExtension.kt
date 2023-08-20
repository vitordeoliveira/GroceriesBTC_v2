package com.example.boxgroceries.utils

fun Double.addAutomaticThousandSeparator()
        = toInt()
    .toString()
    .reversed()
    .chunked(3)
    .joinToString(",")
    .reversed() + if (this % 1 > 0) ".${(this % 1).toString().split(".")[1].take(2)}" else ""

operator fun Double.times(string: String) = if(string.isNotEmpty()) this * string.toDouble() else 0.0