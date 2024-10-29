package com.example.a031_startactivitycontracts

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a031_startactivitycontracts.databinding.ActivityMainBinding

// 그냥 Activity를 실행만 하겠다면 startActivity 로 실행하면 된다
// 그런데 다른 Activity를 갔다 돌아왔을 때 어떠한 작업을 하겠다면
// startActivityForResult 메서드를 통해 Activity를 실행하고
// onActivityResult 메서드를 Overriding 하여 Activity별로 분기하여 처리하면 된다.
// 그러나 이럴 경우 메서드 내부에서 Activity별로 분기하여 각각에 대한 처리를 해줘야 하기 때문에
// 메서드 내부의 코드가 매우 길어질 수 있다.
// 이에 이러한 식의 작업을 할 경우 안드로이드에서는 계약 객체라는 것을 사용한다.
// 계약 객체를 사용할 경우 각 Activity로 별로 각각 코드를 따로따로 만들수 있기 때문에 하나의 메서드 내부의 코드가
// 간결해진다

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 각 Activity별로 실행할 수 있는 Launcher
    lateinit var firstActivityLauncher:ActivityResultLauncher<Intent>
    lateinit var secondActivityLauncher:ActivityResultLauncher<Intent>
    lateinit var thirdActivityLauncher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Activity 실행을 위한 런처는 반드시 onCreate 에서 생성해야 한다.
        // ActivityResultContracts.StartActivityForResult()
        // ActivityResultContracts 클래스는 다양한 계약 객체를 생성하는 메서드를 제공한다.
        // 다른 Activity를 실행하기 위한 계약 객체를 만드는 메서드는 StartActivityForResult() 이다
        firstActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // FirstActivity를 갔다가 돌아왔을 때 동작해야 하는 코드를 구현해준다.
            activityMainBinding.textView4.text = "FirstActivity 실행 후 돌아왔습니다"
            // 작업의 결과로 분기한다.
            when(it.resultCode){
                RESULT_OK -> {
                    if(it.data != null){
                        activityMainBinding.textView4.text = "value1 : ${it.data?.getIntExtra("value1", 0)}"
                    }
                }
                RESULT_CANCELED -> activityMainBinding.textView4.text = "취소되었습니다"
            }
        }

        secondActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            activityMainBinding.textView4.text = "SecondActivity 실행 후 돌아왔습니다"
        }

        thirdActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            activityMainBinding.textView4.text = "ThirdActivity 실행 후 돌아왔습니다"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activityMainBinding.apply {
            button.setOnClickListener {
                // 런처를 통해 Activity를 실행한다.
                val firstIntent = Intent(this@MainActivity, FirstActivity::class.java)
                firstIntent.putExtra("data1", 100)
                firstActivityLauncher.launch(firstIntent)
            }
            button2.setOnClickListener {
                val secondIntent = Intent(this@MainActivity, SecondActivity::class.java)
                secondActivityLauncher.launch(secondIntent)
            }
            button3.setOnClickListener {
                val thirdIntent = Intent(this@MainActivity, ThirdActivity::class.java)
                thirdActivityLauncher.launch(thirdIntent)
            }
        }
    }
}