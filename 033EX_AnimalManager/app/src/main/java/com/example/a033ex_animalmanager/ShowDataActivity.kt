package com.example.a033ex_animalmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a033ex_animalmanager.databinding.ActivityInputDataBinding
import com.example.a033ex_animalmanager.databinding.ActivityShowDataBinding

class ShowDataActivity : AppCompatActivity() {
    lateinit var activityShowDataBinding: ActivityShowDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityShowDataBinding = ActivityShowDataBinding.inflate(layoutInflater)
        setContentView(activityShowDataBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        settingDataByIntent(intent)
        settingBtnDeleteData(intent)
        settingBtnGoHome()
    }


    fun settingDataByIntent(intent: Intent){
        activityShowDataBinding.apply {
            val idx = intent.getIntExtra("idx",0)
            val age = intent.getIntExtra("age",0)
            val list = intent.getStringArrayExtra("favoriteSnacks")
            txtTypeShowData.text = intent.getStringExtra("type")
            txtNameShowData.text = intent.getStringExtra("name")
            txtAgeShowData.text =  age.toString()// 정수형 기본값
            txtGenderShowData.text = intent.getStringExtra("gender")
            list?.forEach {
                txtFavoriteSnacksShowData.append("$it ")
            }
            txtIdx.text = idx.toString()
        }

    }

    fun settingBtnGoHome(){
        activityShowDataBinding.apply {
            btnGoHome.setOnClickListener {
                finish()
            }
        }
    }

    fun settingBtnDeleteData(intent: Intent){
        activityShowDataBinding.apply {
            btnDeleteData.setOnClickListener {
                val idx = intent.getIntExtra("idx",99)
                val deleteIntent = Intent()
                deleteIntent.putExtra("idx", idx)
                setResult(RESULT_OK+1,deleteIntent)
                finish()
            }
        }
    }


}