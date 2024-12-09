package com.lion.a12_mvvm_student

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.lion.a12_mvvm_student.databinding.ActivityInputBinding
import com.lion.a12_mvvm_student.service.StudentService
import com.lion.a12_mvvm_student.viewmodel.InputViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class InputActivity : AppCompatActivity() {
    lateinit var activityInputBinding: ActivityInputBinding
    lateinit var inputViewModel: InputViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityInputBinding = DataBindingUtil.setContentView(this@InputActivity, R.layout.activity_input)
        inputViewModel = InputViewModel(this@InputActivity)
        activityInputBinding.inputViewModel = inputViewModel
        activityInputBinding.lifecycleOwner = this@InputActivity
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Activity를 종료한다.
    fun finishInputActivity(){
        // 데이터를 저장한다.
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                StudentService.addStudentInfo(this@InputActivity, inputViewModel)
            }
            work1.join()
            finish()
        }
    }
}