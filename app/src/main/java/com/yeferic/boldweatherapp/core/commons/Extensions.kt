package com.yeferic.boldweatherapp.core.commons

fun <T> List<T>.getRange(from: Int, to: Int): List<T> {
    val _from = if (from < 0) 0 else from
    val _to = if (to > this.size - 1) this.size else to
    return this.subList(_from, _to)
}
