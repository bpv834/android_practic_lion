package com.example.a033ex_animalmanager

import java.io.Serializable

data class DataModel(var type : String ,var name : String, var age : Int, var gender : String , var favoriteSnack : List<String> ) :
    Serializable {
}