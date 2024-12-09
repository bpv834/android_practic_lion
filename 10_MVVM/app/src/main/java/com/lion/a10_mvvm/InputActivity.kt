package com.lion.a10_mvvm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lion.a10_mvvm.databinding.ActivityInputBinding


class InputActivity : AppCompatActivity() {

    lateinit var activityInputBinding: ActivityInputBinding
    // ViewModel
    lateinit var inputViewModel:InputViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // activityInputBinding = ActivityInputBinding.inflate(layoutInflater)
        // setContentView(activityInputBinding.root)
        // 두 번째 : 사용할 layout 파일
        activityInputBinding = DataBindingUtil.setContentView(this@InputActivity, R.layout.activity_input)
        // ViewModel 객체를 생성한다.
        inputViewModel = InputViewModel(this@InputActivity)
        // ViewModel 객체를 DataBinding 객체에 설정해준다.
        activityInputBinding.inputViewModel = inputViewModel
        // DataBiding 객체의 오너를 설정해준다.
        activityInputBinding.lifecycleOwner = this@InputActivity


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activityInputBinding.apply {
//            buttonFinish.setOnClickListener {
////                val value = editTextInput.text.toString()
////
////                val dataIntent = Intent()
////                dataIntent.putExtra("value", value)
////                setResult(RESULT_OK, dataIntent)
////                finish()
//            }
        }
    }

    // Activity를 종료하는 메서드
    fun activityFinish(){
        val dataIntent = Intent()
        dataIntent.putExtra("value", inputViewModel.editTextInputText.value)
        setResult(RESULT_OK, dataIntent)
        finish()
    }
}