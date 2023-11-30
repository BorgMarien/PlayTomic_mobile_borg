package com.example.playtomic_mobile

import java.util.Date

data class Reservation(
    val Date:String,
    val time:String,
    val Field: Field,
    val IsCompetitive: Boolean,
    val Players: ArrayList<Person>

)