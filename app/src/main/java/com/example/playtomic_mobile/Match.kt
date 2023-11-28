package com.example.playtomic_mobile

data class Match (
    val Date:String,
    val Time:String,
    val Field: Field,
    val IsCompetitive: Boolean,
    val MaxNumberOfPLayers: Int,
    val Players: ArrayList<Person>

)