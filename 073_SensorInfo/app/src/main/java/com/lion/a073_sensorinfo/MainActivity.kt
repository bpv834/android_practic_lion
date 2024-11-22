package com.lion.a073_sensorinfo

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a073_sensorinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityMainBinding.apply {
            // 센서를 관리하는 객체를 추출한다.
            val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
            // 단말기에 있는 센서 목록을 가져온다.
            val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

            textView.text = ""

            // 센서의 수 만큼 반복한다.
            sensorList.forEach {
                textView.append("센서 이름 : ${it.name}\n")
                textView.append("센서 종류 : ${it.type}\n\n")
            }
        }
    }
}