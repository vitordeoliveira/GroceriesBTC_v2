package com.example.groceriesbox.utils

val doublePrecisionRegexp = Regex("^(([\\d]{0,4})(?:\\.{1})?(\\d{0,2}))\$")


fun String.convertToDoublePrecision(oldValue: String) =
    if (this.isEmpty()) {
        this
    } else {
        when (this.toDoubleOrNull()) {
            null -> oldValue //old value
            else -> if(doublePrecisionRegexp.matches(this)) {this} else oldValue  //new value
        }
    }

fun String.convertToInteger(oldValue: String) =
    if (this.isEmpty()) {
        this
    } else {
        when (this.toIntOrNull()) {
            null -> oldValue //old value
            else -> this  //new value
        }
    }
