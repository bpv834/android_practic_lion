package com.lion.a12_mvvm_student

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.lion.a12_mvvm_student.databinding.ActivityShowBinding
import com.lion.a12_mvvm_student.service.StudentService
import com.lion.a12_mvvm_student.viewmodel.ShowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ShowActivity : AppCompatActivity() {
    lateinit var activityShowBinding: ActivityShowBinding
    lateinit var showViewModel: ShowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityShowBinding = DataBindingUtil.setContentView(this@ShowActivity, R.layout.activity_show)
        showViewModel = ShowViewModel(this@ShowActivity)
        activityShowBinding.showViewModel = showViewModel
        activityShowBinding.lifecycleOwner = this@ShowActivity
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 학생 정보를 가져와 출력하는 메서드 호출
        settingTextView()
    }

    // 학생 정보를 가져와 출력하는 메서드
    fun settingTextView(){
        // 학생 번호를 가져온다.
        val studentIdx = intent.getIntExtra("studentIdx", 0)
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                StudentService.showStudentInfoByStudentIdx(this@ShowActivity, studentIdx)
            }
            val studentModel = work1.await()

            // ViewModel에 설정해준다.
            showViewModel.textViewShowName.value = studentModel.studentName
            showViewModel.textViewShowAge.value = studentModel.studentAge.toString()
            showViewModel.textViewShowKorean.value = studentModel.studentKorean.toString()
            showViewModel.textViewShowEnglish.value = studentModel.studentEnglish.toString()
            showViewModel.textViewShowMath.value = studentModel.studentMath.toString()
        }
    }
}