package com.lion.a074_usersensor1

import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a074_usersensor1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // 조도 센서의 리스너
    var lightSensorListener: LightSensorListener? = null

    // 기압 센서
    // 기압 센서의 리스너
    var pressureSensorListener: PressureSensorListener? = null

    // 근접 센서
    var proximitySensorListener: ProximitySensorListener? = null

    // 자이로스코프 센서
    var gyroScopeSensorListener: GyroScopeSensorListener? = null

    // 가속도 센서
    var accelerometerSensorListener: AccelerometerSensorListener? = null

    // 자기장 센서의 리스너
    var magneticFieldSensorListener:MagneticFieldSensorListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //화면 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityMainBinding.apply {
            button.setOnClickListener {
                if (lightSensorListener == null) {
                    // 센서등을 관리하는 객체 가져온다
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 조도 센서에 연결할 수 있는 객체 가져온다
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
                    // 조도 센서에 리스너 연결
                    lightSensorListener = LightSensorListener()
                    // 1. 센서값을 가져올 리스너
                    // 2. 연결할 센서 객체
                    // 3. 값을 가져올 주기
                    // 반환값 : 리스너 연결 성공 여부, 만약 false가 반환됐다면 단말기가 해당 센서를 지원하지 않는 것
                    val chk = sensorManager.registerListener(
                        lightSensorListener,
                        sensor,
                        SensorManager.SENSOR_DELAY_UI
                    )
                    if (!chk) textView.text = "조도 센서를 지원하지 않는다."
                    lightSensorListener = null
                }
            }

            button2.setOnClickListener {
                if (pressureSensorListener == null) {
                    // 센서등을 관리하는 객체 가져온다
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 기압 센서에 연결할 수 있는 객체 가져온다
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
                    // 기압 센서에 리스너 연결
                    pressureSensorListener = PressureSensorListener()
                    // 1. 센서값을 가져올 리스너
                    // 2. 연결할 센서 객체
                    // 3. 값을 가져올 주기
                    // 반환값 : 리스너 연결 성공 여부, 만약 false가 반환됐다면 단말기가 해당 센서를 지원하지 않는 것
                    val chk = sensorManager.registerListener(
                        pressureSensorListener,
                        sensor,
                        SensorManager.SENSOR_DELAY_UI
                    )
                    if (!chk) textView.text = "기압 센서를 지원하지 않는다."
                    pressureSensorListener = null
                }
            }

            button3.setOnClickListener {
                if (proximitySensorListener == null) {
                    // 센서등을 관리하는 객체 가져온다
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 근접 센서에 연결할 수 있는 객체 가져온다
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
                    // 근접 센서에 리스너 연결
                    proximitySensorListener = ProximitySensorListener()
                    // 1. 센서값을 가져올 리스너
                    // 2. 연결할 센서 객체
                    // 3. 값을 가져올 주기
                    // 반환값 : 리스너 연결 성공 여부, 만약 false가 반환됐다면 단말기가 해당 센서를 지원하지 않는 것
                    val chk = sensorManager.registerListener(
                        proximitySensorListener,
                        sensor,
                        SensorManager.SENSOR_DELAY_UI
                    )
                    if (!chk) textView.text = "근접 센서를 지원하지 않는다."
                    proximitySensorListener = null
                }
            }

            button4.setOnClickListener {
                if (gyroScopeSensorListener == null) {
                    // 센서등을 관리하는 객체 가져온다
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 자이로스코프 센서에 연결할 수 있는 객체 가져온다
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
                    // 자이로 스코프 센서에 리스너 연결
                    gyroScopeSensorListener = GyroScopeSensorListener()
                    // 1. 센서값을 가져올 리스너
                    // 2. 연결할 센서 객체
                    // 3. 값을 가져올 주기
                    // 반환값 : 리스너 연결 성공 여부, 만약 false가 반환됐다면 단말기가 해당 센서를 지원하지 않는 것
                    val chk = sensorManager.registerListener(
                        gyroScopeSensorListener,
                        sensor,
                        SensorManager.SENSOR_DELAY_UI
                    )
                    if (!chk) textView.text = "근접 센서를 지원하지 않는다."
                    gyroScopeSensorListener = null
                }
            }

            button5.setOnClickListener {
                if (accelerometerSensorListener == null) {
                    // 센서등을 관리하는 객체 가져온다
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 가속도 센서에 연결할 수 있는 객체 가져온다
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                    // 가속도 센서에 리스너 연결
                    accelerometerSensorListener= AccelerometerSensorListener()
                    // 1. 센서값을 가져올 리스너
                    // 2. 연결할 센서 객체
                    // 3. 값을 가져올 주기
                    // 반환값 : 리스너 연결 성공 여부, 만약 false가 반환됐다면 단말기가 해당 센서를 지원하지 않는 것
                    val chk = sensorManager.registerListener(
                        accelerometerSensorListener,
                        sensor,
                        SensorManager.SENSOR_DELAY_UI
                    )
                    if (!chk) textView.text = "가속도 센서를 지원하지 않는다."
                    accelerometerSensorListener = null
                }
            }

            button6.setOnClickListener {
                if(magneticFieldSensorListener == null){
                    // 센서들을 관리하는 객체를 가져온다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 자기장 센서에 연결할 수 있는 객체를 가져온다.
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
                    // 장기장 센서에 리스너를 연결해준다.
                    magneticFieldSensorListener = MagneticFieldSensorListener()
                    val chk = sensorManager.registerListener(magneticFieldSensorListener, sensor, SensorManager.SENSOR_DELAY_UI)
                    if(chk == false){
                        textView.text = "근접 센서를 지원하지 않는다"
                        magneticFieldSensorListener = null
                    }
                } else {
                    // 센서에 등록된 리스너를 해제한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    sensorManager.unregisterListener(magneticFieldSensorListener)
                    magneticFieldSensorListener = null
                }
            }
        }
    }

    // 조도 센서의 리스너
    // 리스너 객체 필요해 클래스 생성
    inner class LightSensorListener : SensorEventListener {
        // 센서에서 측정된 값이 변경되었을 때 호출되는 메서드
        override fun onSensorChanged(event: SensorEvent?) {
            // 조도 값을 출력한다.
            if (event != null) {
                activityMainBinding.textView.text = "주변 밝기 : ${event.values[0]} lux"
            }
        }

        // 센서의 감도가 변경되었을 때 호출되는 메서드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // 기압 센서의 리스너
    inner class PressureSensorListener : SensorEventListener {
        // 센서에서 측정된 값이 변경되었을 때 호출되는 메서드
        override fun onSensorChanged(event: SensorEvent?) {
            // 공기압 값을 출력한다.
            if (event != null) {
                activityMainBinding.textView.text = "현재 기압 : ${event.values[0]} millibar"
            }
        }

        // 센서의 감도가 변경되었을 때 호출되는 메서드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // 근접 센서의 리스너
    inner class ProximitySensorListener : SensorEventListener {
        // 센서에서 측정된 값이 변경되었을 때 호출되는 메서드
        override fun onSensorChanged(event: SensorEvent?) {
            // 물체 와 거리 값을 출력한다.
            if (event != null) {
                activityMainBinding.textView.text = "물체 거리  : ${event.values[0]} cm"
            }
        }

        // 물체 와 거리 변경되었을 때 호출되는 메서드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // 자이로 스코프 센서의 리스너
    inner class GyroScopeSensorListener : SensorEventListener {
        // 센서에서 측정된 값이 변경되었을 때 호출되는 메서드
        override fun onSensorChanged(event: SensorEvent?) {
            //각속도 값을 출력한다.
            if (event != null) {
                activityMainBinding.textView.text = "X축의 각속도 : ${event.values[0]}"
                activityMainBinding.textView2.text = "Y축의 각속도 : ${event.values[1]}"
                activityMainBinding.textView3.text = "Z축의 각속도 : ${event.values[2]}"
            }
        }

        // 센서의 감도가 변경되었을 때 호출되는 메서드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    //가속도 센서의 리스너
    inner class AccelerometerSensorListener : SensorEventListener {
        // 센서에서 측정된 값이 변경되었을 때 호출되는 메서드
        override fun onSensorChanged(event: SensorEvent?) {
            //회전 각도 값을 출력한다.
            if (event != null) {
                activityMainBinding.textView.text = "X축의 회전 각도 : ${event.values[0]}"
                activityMainBinding.textView2.text = "Y축의 회전 각도 : ${event.values[1]}"
                activityMainBinding.textView3.text = "Z축의 회전 각도 : ${event.values[2]}"
            }
        }

        // 센서의 감도가 변경되었을 때 호출되는 메서드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }
    //자기장 센서의 리스너
    inner class MagneticFieldSensorListener : SensorEventListener{
        // 센서에서 측정된 값이 변경되었을 때 호출되는 메서드
        override fun onSensorChanged(event: SensorEvent?) {
            //자기장 값을 출력한다.
            if(event != null) {
                activityMainBinding.textView.text = "X축의 주변 자기장 : ${event.values[0]}"
                activityMainBinding.textView2.text = "Y축의 주변 자기장: ${event.values[1]}"
                activityMainBinding.textView3.text = "Z축의 주변 자기장 : ${event.values[2]}"
            }
        }

        // 센서의 감도가 변경되었을 때 호출되는 메서드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }
}