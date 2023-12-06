package com.example.playtomic_mobile

data class Match (
    var Name:String,
    var FieldName:String,
    var Date :String,
    var Time:String,
    var isFriendly: Boolean,
    var Players : List<String>

)