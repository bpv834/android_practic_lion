package com.lion.a02_lbs

import com.google.gson.annotations.SerializedName

data class RootClass(
    @SerializedName("results")
    var results:List<ResultsClass>,

    @SerializedName("status")
    var status:String
)

data class ResultsClass(
    @SerializedName("geometry")
    var geometry:GeometryClass,

    @SerializedName("icon")
    var icon:String,

    @SerializedName("name")
    var name:String,

    @SerializedName("vicinity")
    var vicinity:String
)

data class GeometryClass(
    @SerializedName("location")
    var location:LocationClass
)

data class LocationClass(
    @SerializedName("lat")
    var lat:Double,

    @SerializedName("lng")
    var lng:Double
)