package com.example.a041_service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread

class TestService : Service() {

    // 쓰래드 동작 제어
    var isThreadRunning = false

    // 실행중인 서비스에 접속하면 호출되는 메서드(IPC 참고)
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    // 서비스가 가동되면 호출되는 메서드
    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("test100", "Service Start")

        // 안드로이드 8.0 이상 부터..
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // Notification
            addNotificationChannel("Service", "Service")
            val builder1 = makeNotificationBuilder("Service")
            builder1.setSmallIcon(android.R.drawable.ic_menu_add)
            builder1.setContentTitle("서비스 가동")
            builder1.setContentText("서비스가 가동 중입니다")
            val notification = builder1.build()
            // 생성한 Notificaiton 객체를 foreground 서비스 용으로 등록한다.
            startForeground(100, notification)
        }

        // 쓰래드 가동
        thread {
            isThreadRunning = true

            while(isThreadRunning){
                SystemClock.sleep(100)
                Log.d("test100", "Service : ${System.currentTimeMillis()}")
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    // 서비스가 소멸될 때 호출되는 메서드
    override fun onDestroy() {
        super.onDestroy()
        // 쓰래드가 중단될 수 있도록 변수에 false값을 넣어준다.
        isThreadRunning = false
    }

    // Notification 메시지 관리 객체를 생성하는 메서드
    // 매개변수 : Notification 채널 이름
    fun makeNotificationBuilder(channelName:String) : NotificationCompat.Builder{
        // 안드로이드 8.0 이상이라면
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val builder = NotificationCompat.Builder(this@TestService, channelName)
            return builder
        } else {
            val builder = NotificationCompat.Builder(this@TestService)
            return builder
        }
    }

    // Notificaiton Channel을 등록하는 메서드
    // 첫 번째 : 코드에서 채널을 관리하기 위해 사용하는 이름
    // 두 번째 : 사용자에게 노출 시켜줄 이름
    fun addNotificationChannel(id:String, name:String){
        // 안드로이드 8.0 이상일 때만 동작하게 한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 알림 메시지를 관리하는 객체를 추출한다.
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            // 채널 이름을 통해 NotificationChannel 객체를 추출한다.
            var channel = manager.getNotificationChannel(id)
            // 만약 채널이 없다면(등록된 적이 없다면..)
            if(channel == null){
                // 채널 객체를 생성한다.
                channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
                // 메시지 출력시 LED를 사용할 것인가(LED 없는 단말기 많습니다~)
                channel.enableLights(true)
                // LED 색상
                channel.lightColor = Color.RED
                // 진동을 사용할 것인가
                channel.enableVibration(true)
                // 등록한다.
                manager.createNotificationChannel(channel)
            }
        }
    }
}