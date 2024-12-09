package com.lion.a12_mvvm_ex2.util

class Values {

    enum class AnimalType(var number:Int, var str:String){
        ANIMAL_TYPE_DOG(1, "Dog"),
        ANIMAL_TYPE_CAT(2, "Cat"),
        ANIMAL_TYPE_PARROT(3, "parrot")
    }

    enum class AnimalGender(var number:Int, var str:String){
        ANIMAL_TYPE_DOG(1, "Male"),
        ANIMAL_TYPE_CAT(2, "Female"),
    }

}