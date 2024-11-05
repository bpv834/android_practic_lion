package com.example.a048_coroutine

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a048_coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
                // 3개의 쓰래드를 동시에 운영한다(비동기)
                thread {
                    repeat(50) {
                        SystemClock.sleep(100)
                        // 원래는 화면에 관련된 처리는 Main Thread에서 처리해야 한다.
                        // 안드로이드 8.0 부터 화면에 관련된 작업은 자동으로 Main Thread에 할당된다.
                        // 혹시나 하는 마음에 runOnUiThread를 사용한다
                        runOnUiThread {
                            textView2.text = "Thread1 : ${System.currentTimeMillis()}"
                        }
                    }
                }
                // 3개의 쓰래드를 동시에 운영한다(비동기)
                thread {
                    repeat(50) {
                        SystemClock.sleep(100)
                        // 원래는 화면에 관련된 처리는 Main Thread에서 처리해야 한다.
                        // 안드로이드 8.0 부터 화면에 관련된 작업은 자동으로 Main Thread에 할당된다.
                        // 혹시나 하는 마음에 runOnUiThread를 사용한다
                        runOnUiThread {
                            textView3.text = "Thread1 : ${System.currentTimeMillis()}"
                        }
                    }
                }
                // 3개의 쓰래드를 동시에 운영한다(비동기)
                thread {
                    repeat(50) {
                        SystemClock.sleep(100)
                        // 원래는 화면에 관련된 처리는 Main Thread에서 처리해야 한다.
                        // 안드로이드 8.0 부터 화면에 관련된 작업은 자동으로 Main Thread에 할당된다.
                        // 혹시나 하는 마음에 runOnUiThread를 사용한다
                        runOnUiThread {
                            textView.text = "Thread1 : ${System.currentTimeMillis()}"
                        }
                    }
                }

            }

            button2.setOnClickListener {
                // 3개의 쓰래드를 순차적으로 운영한다(동기)
                // join을 호출하면 현재 쓰래드의 작업이 끝날 때 까지 다른 쓰래드의 작업을 대기한다.
                // main쓰레드도 작업을 대기하기 때문에 ui가 안바뀐다.
                thread {
                    repeat(50) {
                        SystemClock.sleep(100)
                        runOnUiThread {
                            textView.text = "Thread1 : ${System.currentTimeMillis()}"
                        }
                    }
                }.join()

                thread {
                    repeat(50) {
                        SystemClock.sleep(100)
                        runOnUiThread {
                            textView2.text = "Thread2 : ${System.currentTimeMillis()}"
                        }
                    }
                }.join()

                thread {
                    repeat(50) {
                        SystemClock.sleep(100)
                        runOnUiThread {
                            textView3.text = "Thread3 : ${System.currentTimeMillis()}"
                        }
                    }
                }.join()
            }

            button3.setOnClickListener {
                // 3개의 코루틴을 동시에 운영한다(비동기)
                // CoroutineScope : 필요할때만 잠깐 사용하는 코루틴을 발생시킬때 사용한다.
                CoroutineScope(Dispatchers.IO).launch {
                    repeat(50) {
                        SystemClock.sleep(100)
                        CoroutineScope(Dispatchers.Main).launch {
                            textView.text = "Coroutine1 : ${System.currentTimeMillis()}"
                        }
                    }
                }

                CoroutineScope(Dispatchers.IO).launch {
                    repeat(50) {
                        SystemClock.sleep(100)
                        CoroutineScope(Dispatchers.Main).launch {
                            textView2.text = "Coroutine2 : ${System.currentTimeMillis()}"
                        }
                    }
                }

                CoroutineScope(Dispatchers.IO).launch {
                    repeat(50) {
                        SystemClock.sleep(100)
                        CoroutineScope(Dispatchers.Main).launch {
                            textView3.text = "Coroutine3 : ${System.currentTimeMillis()}"
                        }
                    }
                }
            }

            button4.setOnClickListener {
                // 3개의 코루틴을 순차적으로 운영한다(동기)
                val job1 = CoroutineScope(Dispatchers.IO).launch {
                    repeat(50) {
                        SystemClock.sleep(100)
                        CoroutineScope(Dispatchers.Main).launch {
                            textView.text = "Coroutine1 : ${System.currentTimeMillis()}"
                        }
                    }
                }
                runBlocking {
                    job1.join()
                }


                val job2 = CoroutineScope(Dispatchers.IO).launch {
                    repeat(50) {
                        SystemClock.sleep(100)
                        CoroutineScope(Dispatchers.Main).launch {
                            textView2.text = "Coroutine2 : ${System.currentTimeMillis()}"
                        }
                    }
                }
                runBlocking {
                    job2.join()
                }

                val job3 = CoroutineScope(Dispatchers.IO).launch {
                    repeat(50) {
                        SystemClock.sleep(100)
                        CoroutineScope(Dispatchers.Main).launch {
                            textView3.text = "Coroutine3 : ${System.currentTimeMillis()}"
                        }
                    }
                }
                runBlocking {
                    job3.join()
                }
            }


        }
    }
}