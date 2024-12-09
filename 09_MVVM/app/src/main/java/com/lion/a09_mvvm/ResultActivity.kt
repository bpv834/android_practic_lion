package com.lion.a09_mvvm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.lion.a09_mvvm.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var activityResultBinding: ActivityResultBinding
    // ViewModel 객체를 담을 변수
    lateinit var resultViewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(activityResultBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // MVVM 설정 메서드 호출
        settingMVVM()

        activityResultBinding.apply {
            val data = intent.getStringExtra("data")!!
            //textViewResult.text = data
            // ViewModel 객체가 가지고 있는 MutableLiveData에 값을 셋팅한다.
            resultViewModel.textViewResultText.value = data
        }
    }

    // MVVM 설정
    // ViewModel 객체 생성
    // ViewModel이 가지고 있는 MutableLiveData에 감시자를 설정한다.
    // 감시자에는 새로운 값이 저장되었을 경우 동작할 리스너를 설정한다.
    fun settingMVVM(){
        // ViewModel의 객체를 생성한다
        // owner : ViewModel 객체를 소유하는 소유자.
        // ViewModel 객체를 소유자의 라이프 사이클을 따른다. Activity를 지정했을 경우
        // Activity가 소멸될 때 객체도 같이 소멸된다.
        resultViewModel = ViewModelProvider(this@ResultActivity)[ResultViewModel::class.java]

        // ViewModel이 가지고 있는 MutableLiveData에 감시자를 붙혀준다.
        // 감시자를 붙혀주면 MutableLiveData에 새로운 값을 저장할 경우 감시자의 코드가 동작한다.
        // it 에는 MutableLiveData에 저장된 값이 들어온다.
        resultViewModel.textViewResultText.observe(this@ResultActivity){
            activityResultBinding.textViewResult.text = it
        }
    }
}