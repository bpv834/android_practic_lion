package com.lion.ipc

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread
class TestService : Service() {

    // 값을 담을 변수
    var value = 0
    // 쓰래드 반복 관리 변수
    var isRunning = false

    // 서비스에 접속하면 전달할 객체를 생성한다.
    val binder = LocalBinder()

    // 서비스에 접속하면 호출되는 메서드
    // 여기서 반환하는 Binder 객체가 Activity 로 전달된다.
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    // 서비스가 가동될 때 호출되는 메서드
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        addNotificationChannel("Service", "Service")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val builder1 = makeNotificationBuilder("Service")
            builder1.setSmallIcon(android.R.drawable.ic_menu_edit)
            builder1.setContentTitle("서비스 가동")
            builder1.setContentText("서비스가 가동 중 입니다")
            val notification = builder1.build()
            startForeground(10, notification)
        }

        // 쓰래드를 가동시킨다.
        isRunning = true

        thread {
            while(isRunning){
                SystemClock.sleep(100)
                Log.d("test100", "Service Running : $value")
                value++
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 쓰래드의 반복문을 중단시키기 위해 변수에 false를 넣어준다.
        isRunning = false
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

    // 접속하는 Activity에서 서비스를 추출하기 위해 사용하는 객체
    inner class LocalBinder : Binder(){
        // 현재의 서비스 객체를 반환하는 메서드를 만들어준다.
        fun gettingService():TestService{
            return this@TestService
        }
    }
}