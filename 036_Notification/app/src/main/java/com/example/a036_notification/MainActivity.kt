package com.example.a036_notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a036_notification.databinding.ActivityMainBinding

// Notificaiton
// 단말기의 알림 영역에 표시할 수 있는 메시지
// 다양한 스타일을 제공하고 있고 메시지를 누르면 지정된 Acitivity를 실행할 수도 있다.
// 메시지를 통해 어플 실행을 유도하고자 할 때 사용한다.
// 사용자가 메시지를 확인할 때 까지 메시지는 계속 남아있다(재부팅해도 남아있다)

// Android 13 버전 부터는 Notification 사용을 위해 권한을 추가해줘야 하고 권한에 대한 확인을 받아야 한다.

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // android 13  버전
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // 권한 목록
            val permissionList = arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            )
            requestPermissions(permissionList, 0)
        }

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Notification Channel을 등록한다.
        addNotificationChannel("channel1", "채널1")
        addNotificationChannel("channel2", "채널2")

        activityMainBinding.apply {
            button.setOnClickListener {
                // 알림 권한이 비활성화 되어 있다면 종료한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // POST_NOTIFICATION 권한의 허용 어부값을 가져온다
                    val check = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    // 권한이 거부되어 있다면 함수의 수행을 중단시킨다.
                    if (check == PackageManager.PERMISSION_DENIED) {
                        return@setOnClickListener
                    }
                }

                // Notification Builder를 가져온다.
                val builder = makeNotificationBuilder("channel1")
                // 작은 아이콘(메시지 수신시 상단 status bar에 보여줄 작은 아이콘. 안드로이드 버전에 따라 노출되지 않을 수 있다)
                builder.setSmallIcon(android.R.drawable.ic_menu_add)
                // 큰 아이콘 (메시지 본문에 표시할 이미지. Bitmap 객체)
                val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                builder.setLargeIcon(bitmap)
                // 숫자 설정
                builder.setNumber(100)
                // 타이틀 설정
                builder.setContentTitle("Content Title 1")
                // 메시지 설정
                builder.setContentText("Content Text 1")

                // 메시지 객체를 생성
                val notification = builder.build()
                // 알림메시지를 출력한다.
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                // 첫 번째 : 메시지 번호. 이 번호를 부여하여 이미 떠있는 메시지가 있다면 그 자리에 메시지를 갱신하고
                // 없다면 새롭게 띄워준다.
                // 두 번째 : 메시지 내용이 담긴 객체
                manager.notify(100, notification)
            }

            button3.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // 애플리케이션이 POST_NOTIFICATIONS 권한을 가지고 있는지 확인하는 코드입니다
                    // 이 메서드는 특정 권한에 대해 앱이 현재 허용되어 있는지 검사합니다. 이 메서드는 PERMISSION_GRANTED 또는 PERMISSION_DENIED 값을 반환한다.
                    val check = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    if (check == PackageManager.PERMISSION_DENIED) {
                        return@setOnClickListener
                    }
                }

                val builder = makeNotificationBuilder("channel1")
                builder.setSmallIcon(android.R.drawable.ic_menu_add)
                builder.setNumber(100)
                builder.setContentTitle("Content Title 3")
                builder.setContentText("Content Text 3")

                // 메시지를 누르면 메시지가 자동으로 사라지게 한다.
                builder.setAutoCancel(true)

                // 실행할 Activity의 정보를 가지고 있는 Intent를 생성한다.
                val newIntent = Intent(this@MainActivity, TestActivity::class.java)
                newIntent.putExtra("data1", 100)
                newIntent.putExtra("data2", 200)

                // PendingIntent 생성
                // PendingIntent : Activity를 실행할 수 있는 Notification의 Intent
                // FLAG_UPDATE_CURRENT : PendingIntent가 존재할 경우 데이터만 모두 대체한다(새로운 PendingIntent를 만들지 않는다)
                // FLAG_CANCEL_CURRENT : PendingIntent가 존재할 경우 기존의 PendingIntent를 소멸시키고 새롭게 생성한다.
                // FLAG_IMMUTABLE : PendingIntent를 전송하고 그 이후에 다시 전송하는 것들은 무시한다.
                // FLAG_NO_CREATE : PendingIntent가 기존에 존재하지 않으면 취소 시킨다
                // FLAG_ONT_SHOT : PendingIntent를 전송하고 소멸된다.
                val pending1 = PendingIntent.getActivity(
                    this@MainActivity,
                    10,
                    newIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
                builder.setContentIntent(pending1)

                // Action : 메시지에 버튼 같은 것을 추가할 수 있다.
                // PendingIntent를 설정할 때 Activity를 지정하면 Activity를 실행할 수 있고
                // Service를 지정하면 마치 버튼 처럼 사용할 수 있다.
                // 실행할 Activity의 정보를 가지고 있는 Intent를 생성한다.
                val actionIntent = Intent(this@MainActivity, TestActivity::class.java)
                actionIntent.putExtra("data1", 1000)
                actionIntent.putExtra("data2", 2000)

                val pending2 = PendingIntent.getActivity(
                    this@MainActivity,
                    20,
                    actionIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
                // Action 생성
                val action1 = NotificationCompat.Action.Builder(
                    // 아이콘
                    android.R.drawable.ic_menu_add,
                    //표시 문자열
                    "Action 1",
                    pending2,
                )
                // Action 추가
                builder.addAction(action1.build())


                val notification = builder.build()
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                manager.notify(300, notification)
            }

            button4.setOnClickListener {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    val check = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    if(check == PackageManager.PERMISSION_DENIED){
                        return@setOnClickListener
                    }
                }

                val builder = makeNotificationBuilder("channel1")
                builder.setSmallIcon(android.R.drawable.ic_menu_add)
                builder.setNumber(100)
                builder.setContentTitle("Big Picture")
                builder.setContentText("Big Picture Notification")

                // Big Picture Notification 을 생성한다.
                val big = NotificationCompat.BigPictureStyle(builder)
                // 보여줄 이미지를 설정한다.
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_android)
                big.bigPicture(bitmap)
                big.setBigContentTitle("Big Content Title")
                big.setSummaryText("Summary Text")

                val notification = builder.build()
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                manager.notify(400, notification)
            }

            button5.setOnClickListener {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    val check = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    if(check == PackageManager.PERMISSION_DENIED){
                        return@setOnClickListener
                    }
                }

                val builder = makeNotificationBuilder("channel1")
                builder.setSmallIcon(android.R.drawable.ic_menu_add)
                builder.setNumber(100)
                builder.setContentTitle("Big Text")
                builder.setContentText("Big Text Notification")

                // Big Text Notification 을 생성한다.
                val big = NotificationCompat.BigTextStyle(builder)
                // 보여줄 문자열을 설정한다.
                big.bigText(""" 동해물과
                    |백두산이
                    |마르고
                    |닳도록
                """.trimMargin())
                big.setBigContentTitle("Big Content Title")
                big.setSummaryText("Summary Text")

                val notification = builder.build()
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                manager.notify(500, notification)
            }

            button6.setOnClickListener {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    val check = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    if(check == PackageManager.PERMISSION_DENIED){
                        return@setOnClickListener
                    }
                }

                val builder = makeNotificationBuilder("channel1")
                builder.setSmallIcon(android.R.drawable.ic_menu_add)
                builder.setNumber(100)
                builder.setContentTitle("Inbox")
                builder.setContentText("InBox Notification")

                // Big Text Notification 을 생성한다.
                val inBox = NotificationCompat.InboxStyle(builder)
                // 보여줄 문자열을 설정한다.

                inBox.addLine("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                inBox.addLine("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
                inBox.addLine("ccccccccccccccccccccccccccccc")
                inBox.addLine("dddddddddddddddddddddddddddddddddd")


                inBox.setBigContentTitle("Big Content Title")
                inBox.setSummaryText("Summary Text")

                val notification = builder.build()
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                manager.notify(600, notification)
            }

            button7.setOnClickListener {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    val check = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    if(check == PackageManager.PERMISSION_DENIED){
                        return@setOnClickListener
                    }
                }

                val builder = makeNotificationBuilder("channel1")
                builder.setSmallIcon(android.R.drawable.ic_menu_add)
                builder.setNumber(100)
                builder.setContentTitle("Message")
                builder.setContentText("Message Notification")

                val personBuilder1 = Person.Builder()
                //사람에 대한 아이콘
                val icon1 = IconCompat.createWithResource(this@MainActivity, android.R.drawable.ic_menu_add)
                //사람 이름
                personBuilder1.setName("홍길동")
                val person = personBuilder1.build()

                val personBuilder2 = Person.Builder()
                val icon2 = IconCompat.createWithResource(this@MainActivity, android.R.drawable.ic_menu_call)
                personBuilder2.setName("김길동")
                val person2 = personBuilder2.build()

                //Message Notification을 생성한다.
                val msg = NotificationCompat.MessagingStyle(person)
                // 대화 내용을 설정한다.
                //대화 내용, 보낸 시간, 사람 객체
                msg.addMessage("안녕하세요",System.currentTimeMillis(),person)
                msg.addMessage("반값습니다.", System.currentTimeMillis(),person2)
                msg.addMessage("도아냐",System.currentTimeMillis(),person)
                msg.addMessage("레도 안다.", System.currentTimeMillis(),person2)

                builder.setStyle(msg)

                val notification = builder.build()
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                manager.notify(600, notification)
            }

        }
    }

    // Notification 메시지 관리 객체를 생성하는 메서드
    // 매개변수 : Notification 채널 이름
    fun makeNotificationBuilder(channelName: String): NotificationCompat.Builder {
        // 안드로이드 8.0 이상이라면
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val builder = NotificationCompat.Builder(this@MainActivity, channelName)
            return builder
        } else {
            val builder = NotificationCompat.Builder(this@MainActivity)
            return builder
        }
    }

    // Notificaiton Channel을 등록하는 메서드
    // 첫 번째 : 코드에서 채널을 관리하기 위해 사용하는 이름
    // 두 번째 : 사용자에게 노출 시켜줄 이름
    fun addNotificationChannel(id: String, name: String) {
        // 안드로이드 8.0 이상일 때만 동작하게 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 알림 메시지를 관리하는 객체를 추출한다.
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            // 채널 이름을 통해 NotificationChannel 객체를 추출한다.
            var channel = manager.getNotificationChannel(id)
            // 만약 채널이 없다면(등록된 적이 없다면..)
            if (channel == null) {
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







