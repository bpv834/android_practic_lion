package com.example.a046_sidesheet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a046_sidesheet.databinding.ActivityMainBinding
import com.example.a046_sidesheet.databinding.SideSheetBinding
import com.google.android.material.sidesheet.SideSheetDialog

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
                // sideSheetDialog를 생성
                val sideSheetDialog = SideSheetDialog(this@MainActivity)
                // 사용할 ViewBinding
                val sideSheetBinding = SideSheetBinding.inflate(layoutInflater)
                // View를 설정해준다.
                sideSheetDialog.setContentView(sideSheetBinding.root)
                // 화면의 아무대나 누를 경우 사라지는 것을 막는다.
                sideSheetDialog.setCancelable(false)
                // SideSheet 바깥쪽을 누르면 사라지게 한다.
                sideSheetDialog.setCanceledOnTouchOutside(true)

                sideSheetBinding.buttonSideSheet.setOnClickListener {
                    // 사라지게 한다.
                    sideSheetDialog.dismiss()
                }
                // SideSheet를 띄운다.
                sideSheetDialog.show()
            }

        }
    }
}