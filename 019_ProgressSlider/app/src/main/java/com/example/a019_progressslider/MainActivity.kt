package com.example.a019_progressslider

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a019_progressslider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
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
                // Progresss Indicator에 현재 설정되어 있는 값을 가지고 온다.
                textView.text = "${progress1.progress}, ${progress2.progress}"
                //slider 에 설정된 값 가져오기
                textView2.text = "${slider1.value}"
                // 슬라이더 설정값 가져오기
                textView3.text = "${slider2.values[0]}~${slider2.values[1]}"
            }
            button2.setOnClickListener{
                // progress indicator 값 설정
                progress1.progress = 150
                progress2.progress = 150

                //slider의 값 설정
                slider1.value = 80.0f

            }
            button3.setOnClickListener {
                progress1.incrementProgressBy(10)
                progress2.incrementProgressBy(10)
            }
            button4.setOnClickListener {
                progress1.incrementProgressBy(-10)
                progress2.incrementProgressBy(-10)
            }
            //value : 설정된 값
            // fromUser : 사용자ㅓ에 의해서 값이 설정됐는가?
            slider1.addOnChangeListener { slider, value, fromUser ->
                textView.text = "slider1 : $value"
                textView2.text = if(fromUser){
                    "사용자에 의해 설정됐다."
                }else{
                    "코드를 통해 설정됐다"
                }
            }
            // RangeSlider에 리스너를 사용하면 value 에는 값이 변경된 최소 값이나
            // 최대 값 중 하나만 들어오게 된다.
            // 만약 변경된 것만 필요하다면 value를 사용하면 되는데
            // 코드 상에서 최대값이 변경되었는지 최소값이 변경되었는지를 구분할 수 없다.
            // 저는 value 쓰지 않고 슬라이더에서 직접 최소와 최대값을 가져다 사용하고 있습니다
            slider2.addOnChangeListener { slider, value, fromUser ->
                textView.text = "${slider2.values[0]} ~ ${slider2.values[1]}"
                textView2.text = if(fromUser){
                    "사용자에 의해 설정되었습니다"
                } else {
                    "코드를 통해 설정되었습니다"
                }
            }
        }
    }
}