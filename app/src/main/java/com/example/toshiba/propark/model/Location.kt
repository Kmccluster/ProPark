package com.example.toshiba.propark.model

data class Location(
    var Available: Int? = 1,
    var Booked: Int? = 0,
    var areaName: String? = null
) {
}