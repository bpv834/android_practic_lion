package com.lion.a075_compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a075_compass.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 가속도 센서로 측정한 값을 담을 배열
    val accValue = floatArrayOf(0.0f, 0.0f, 0.0f)

    // 자기장 센서로 측정한 값을 담을 배열
    val magValue = floatArrayOf(0.0f, 0.0f, 0.0f)

    // 측정된 적이 있는가?
    var isGetAcc = false
    var isGetMag = false

    var accelerometerSensorListener: AccelerometerSensorListener? = null
    var magneticFieldSensorListener: MagneticFieldSensorListener? = null


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
            button.setOnClickListener {
                // 센서를 가동시킨다.
                val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

                val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                val magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

                accelerometerSensorListener = AccelerometerSensorListener()
                magneticFieldSensorListener = MagneticFieldSensorListener()

                sensorManager.registerListener(
                    accelerometerSensorListener,
                    accSensor,
                    SensorManager.SENSOR_DELAY_UI
                )
                sensorManager.registerListener(
                    magneticFieldSensorListener,
                    magSensor,
                    SensorManager.SENSOR_DELAY_UI
                )
            }

            button2.setOnClickListener {
                if (isGetAcc == true and isGetMag == true) {
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    sensorManager.unregisterListener(accelerometerSensorListener)
                    sensorManager.unregisterListener(magneticFieldSensorListener)

                    accelerometerSensorListener = null
                    magneticFieldSensorListener = null
                }
            }
        }
    }


    // 가속도 센서의 리스너
    inner class AccelerometerSensorListener : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            // 측정된 값을 배열에 담아준다.
            if (event != null) {
                accValue[0] = event.values[0]
                accValue[1] = event.values[1]
                accValue[2] = event.values[2]

                isGetAcc = true
                getAzimuth()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // 자기장 센서의 리스너
    inner class MagneticFieldSensorListener : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            // 측정이 되었다면 배열에 담는다.
            if (event != null) {
                magValue[0] = event.values[0]
                magValue[1] = event.values[1]
                magValue[2] = event.values[2]

                isGetMag = true
                getAzimuth()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

    }

    // 방위값을 계산하는 메서드
    fun getAzimuth() {
        // 두 센서 모두 측정한 적이 있다면
        if (isGetAcc == true and isGetMag) {
            val R = FloatArray(9)
            val I = FloatArray(9)
            // 계산 행렬 값을 구한다.

            SensorManager.getRotationMatrix(R, I, accValue, magValue)
            // 계산 행렬을 이용하여 방위값을 구한다.
            val values = FloatArray(3)
            SensorManager.getOrientation(R, values)

            // 결과가 라디언 값으로 나오기 때문에 각도 값으로 변환한다.
            // azimuth : 방위값 (-180 ~ 180)
            // pitch : 좌우 기울기 값
            // roll : 앞뒤 기울기 값
            var azimuth = Math.toDegrees(values[0].toDouble())
            val pitch = Math.toDegrees(values[1].toDouble())
            val roll = Math.toDegrees(values[2].toDouble())

            // 계산된 방위값에 180을 더해준다. (-180 ~ 180) 범위기 때문에
            azimuth = azimuth + 180

            // 이미지 뷰의 회전 값
            // 회전을 반대로 줘야 맞게 표현되는걸로 보이기 때문에 역회전
            val rotationValue = (360 - azimuth).toFloat()
            activityMainBinding.imageView.rotation = rotationValue
        }
    }


}