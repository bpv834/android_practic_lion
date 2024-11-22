package com.lion.handler

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.handler.databinding.ActivityMainBinding
import kotlin.concurrent.thread
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
            button.setOnClickListener {
                textView.text = "버튼 클릭 : ${System.currentTimeMillis()}"
            }

            button2.setOnClickListener {
                // 화면에 표시되는 UI 요소에 대한 작업을 지속적으로 해야할 경우
                // Activity나 프래그먼트가 보여지고 있는 상황에서 UI 요소가 현재 보이는 화면이
                // 사라질때까지 지속적으로 반복해서 작업을 해야 하는 경우
                // 반복시 작업 한번의 시간이 오래 걸리지 않는 경우

                // 쓰래드 사용
//                thread {
//                    while(true) {
//                        SystemClock.sleep(100)
//                        // 현재 시간을 구한다.
//                        val now = System.currentTimeMillis()
//                        // 화면에 출력한다.
//                        // Main Thread에서 처리한다.
//                        runOnUiThread {
//                            textView2.text = "Thread : $now"
//                        }
//                    }
//                }

                // Handler 객체 생성
                val handler = Handler()
                // Handler가 처리할 작업.(반복적으로 처리할 코드를 만들어준다)
                val t1 = object : Thread(){
                    override fun run() {
                        super.run()
                        // 여기의 코드는 MainThread에서 처리한다.
                        SystemClock.sleep(100)
                        // 현재 시간을 구한다.
                        val now = System.currentTimeMillis()
                        textView2.text = "Handler : $now"
                        // 현재 작업을 다시 요청한다.
                        handler.post(this)
                    }
                }
                // handler로 쓰래드 내부의 코드를 처리하도록 요청한다(MainThread가 처리해준다)
                handler.post(t1)
                // handler로 쓰래드 내부의 코드를 처리하도록 요청한다(MainThread가 처리해준다)
                // handler.post(t1)
                // 100ms 후에 작업을 처리하도록 요청한다.
                handler.postDelayed(t1, 100)
            }

            button3.setOnClickListener {
                // Handler를 상속받은 클래스
                val handlerClass = object : Handler(){
                    // Handler 작업 요청이 발생하면 호출되는 메서드
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        // 작업을 요청할 때 전달한 정수값(what)을 이용해
                        // 분기하여 여러 작업을 구현해서 제공할 수 있다.
                        when(msg.what){
                            0 -> {
                                val now = System.currentTimeMillis()
                                textView2.text = "Handler : $now"
                            }
                            1-> {
                                val r1 = 100 + 200
                                textView2.text = "100 + 200 = $r1"
                            }
                            3->{
                                val msg = Message()
                                msg.what = 2
                                msg.obj = "문자열"
                                msg.arg1 = 100
                            }
                        }
                    }
                }

                // Thread
                // Thread에서 오래 걸리는 작업이나 지속적으로 반복하는 작업을 수행할 때
                // 중간 중간 화면작업이 필요할 경우 Handler에게 작업 요청을 할 수 있다
                thread {
                    while(true){
                        SystemClock.sleep(100)
                        // 핸들러에게 작업을 요청한다.
                        handlerClass.sendEmptyMessage(0)
                        SystemClock.sleep(100)
                        handlerClass.sendEmptyMessage(1)

                        val msg = Message()
                        msg.what = 2
                        msg.obj = "문자열"
                        msg.arg1 = 100
                        msg.arg2 = 200
                        SystemClock.sleep(500)
                        handlerClass.sendMessage(msg)
                    }
                }

            }
        }

    }
}