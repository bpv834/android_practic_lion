package com.lion.a072_deviceinfo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a072_deviceinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    // 확인할 권한 목록
    val permissionList = arrayOf(
        android.Manifest.permission.READ_PHONE_STATE,
        android.Manifest.permission.READ_SMS,
        android.Manifest.permission.READ_PHONE_NUMBERS
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //권한 확인 요청
        requestPermissions(permissionList,0)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityMainBinding.apply {
            button.setOnClickListener {
                // 전화 정보를 관리하는 객체를 추출한다.
                val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

                // 사용자가 권한을 허용했는지 확인해야 한다.
                val c1 = ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.READ_SMS)
                // 권한이 거부되어 있다면..
                if(c1 == PackageManager.PERMISSION_DENIED){
                    return@setOnClickListener
                }

                // 전화번호
                textView.text = "전화번호 : ${telephonyManager.line1Number}\n"
                // SIM 국가 코드
                textView.append("SIM 국가 코드 : ${telephonyManager.simCountryIso}\n")
                // 모바일 국가 코드 + 모바일 네트워크 코드
                textView.append("모바일 국가 코드 + 모바일 네트워크 코드 : ${telephonyManager.simOperator}\n")
                // 서비스 이름
                textView.append("서비스 이름 : ${telephonyManager.simOperatorName}\n")
                // SIM 상태(통신 가능 여부, PIN Lock 여부)
                textView.append("SIM 상태 : ${telephonyManager.simState}\n")
                // 음성 메일 번호
                textView.append("음성 메일 번호 : ${telephonyManager.voiceMailNumber}")
            }

            button2.setOnClickListener {
                textView.text = "보드 이름 : ${Build.BOARD}\n"
                textView.append("소프트웨어를 커스터마이징한 회사 : ${Build.BRAND}\n")
                textView.append("제조사 디자인 명 : ${Build.DEVICE}\n")
                textView.append("사용자에게 표시되는 빌드 ID : ${Build.DISPLAY}\n")
                textView.append("빌드 고유 ID : ${Build.FINGERPRINT}\n")
                textView.append("ChangeList 번호 : ${Build.ID}\n")
                textView.append("제품/하드웨어 제조업체 : ${Build.MANUFACTURER}\n")
                textView.append("제품 명 : ${Build.PRODUCT}\n")
                textView.append("빌드 구분 : ${Build.TAGS}\n")
                textView.append("빌드 타입 : ${Build.TYPE}\n")
                textView.append("안드로이드 버전 : ${Build.VERSION.RELEASE}\n")

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    textView.append("SoC 제조업체 : ${Build.SOC_MANUFACTURER}\n")
                    textView.append("SoC 모델명 : ${Build.SOC_MODEL}\n")
                }
            }

            button3.setOnClickListener {
                // 해상도 정보를 가지고 있는 객체를 추출한다.
                val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                    // 11 버전 이상인 경우
                    // 가로 길이
                    val width = windowManager.currentWindowMetrics.bounds.width()
                    // 세로 길이
                    val height = windowManager.currentWindowMetrics.bounds.height()

                    textView.text = "가로 길이 : ${width}\n"
                    textView.append("세로 길이 : ${height}")
                } else {
                    // 10 버전 이하인 경우
                    // 메인 화면 관련 객체를 가져온다.
                    val display = windowManager.defaultDisplay
                    // 해상도 정보를 가지고 있는 객체를 가져온다.
                    val point = Point()
                    // 가로 세로 길이를 담는다.
                    display.getSize(point)

                    textView.text = "가로 길이 : ${point.x}\n"
                    textView.append("세로 길이 : ${point.y}")
                }
            }
        }
    }
}