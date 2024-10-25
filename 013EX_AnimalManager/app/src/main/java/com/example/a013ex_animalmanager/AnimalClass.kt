package com.example.a013ex_animalmanager

import android.util.Log

class AnimalClass(val name: String, val type: String, val age: Int) {

    //정보를 출력하는 메서드
    fun showAnimalInfo() {
        Log.d("animal", "이름 : $name")
        Log.d("animal", "타입 : $type")
        Log.d("animal", "나이 : $age")
        Log.d("animal", "---------------------------")
    }

}