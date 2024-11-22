package com.lion.ipc

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.ipc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 접속한 서비스 객체를 담을 변수
    var ipcService: TestService? = null

    // 서비스 접속을 관리하는 객체
    lateinit var serviceConnectionClass: ServiceConnectionClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // android 13 버전 이상
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionList = arrayOf(
                android.Manifest.permission.POST_NOTIFICATIONS
            )
            requestPermissions(permissionList, 0)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 서비스가 가동 중인지 확인한다.
        // 서비스의 클래스 이름을 넣어준다.
        val chk = isServiceRunning("com.lion.a06_ipc.TestService")
        // Log.d("test100", "chk : $chk")

        // 서비스 가동을 위한 Intent
        val serviceIntent = Intent(this@MainActivity, TestService::class.java)
        // 서비스가 가동중이 아니라면 서비스를 가동한다.
        if (chk == false) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
        }
        // 서비스에 접속한다.
        serviceConnectionClass = ServiceConnectionClass()
        bindService(serviceIntent, serviceConnectionClass, BIND_AUTO_CREATE)
        activityMainBinding.apply {
            button.setOnClickListener {
                // 서비스의 메서드를 호출하여 값을 가져온다.
                if(ipcService != null){
                    textView.text = "sevice - value : ${ipcService?.value}"
                }
            }
        }

    }

    // 서비스가 가동 중임을 확인하는 메서드
    fun isServiceRunning(name: String): Boolean {
        // 서비스 관리자를 추출gksek.
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        // 현재 동작 중인 모든 서비스를 관리하는 객체를 추출한다.
        val serviceList = activityManager.getRunningServices(Int.MAX_VALUE)
        // 가져온 서비스 정보 객체의 수 만큼 반복한다.
        serviceList.forEach {
            // 현재 서비스의 클래스 이름과 매개변수로 들어온 클래스의 이름이 동일하다면
            if (it.service.className == name) {
                return true
            }
        }
        return false
    }

    // Activit에서 서비스 접속을 관리하는 클래스
    inner class ServiceConnectionClass : ServiceConnection {
        // 서비스에 접속이 성공하면 호출되는 메서드
        // 두 번째 : 서비스의 onBind 메서드가 반환하는 객체가 들어온다.
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 매개변수로 들어온 Binder 객체에서 서비스를 추출해 담아준다.
            val localBinder = service as TestService.LocalBinder
            ipcService = localBinder.gettingService()
        }

        // 서비스 접속이 해제되면 호출되는 메서드
        override fun onServiceDisconnected(name: ComponentName?) {
            ipcService = null
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        // 서비스 접속을 해제한다.
        unbindService(serviceConnectionClass)
    }

}