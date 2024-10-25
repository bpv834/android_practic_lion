package com.example.a013ex_2_carmanager

import android.util.Log

class CarClass(val name : String, val type : String , val wheelCnt : Int) {

    //정보를 출력하는 메서드
    fun showCarInfo() {
        Log.d("car", "이름 : $name")
        Log.d("car", "타입 : $type")
        Log.d("car", "바퀴 개수 : $wheelCnt")
        Log.d("car", "----------------------------------")

    }

}